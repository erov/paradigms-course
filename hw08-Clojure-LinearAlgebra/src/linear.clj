(defn get-dim [obj]
  (if (or (number? obj) (empty? obj))
    (list)
    (cons (count obj) (get-dim (first obj)))))

(defn check-dims-equality [dims obj]
  (or
    (or (number? obj) (empty? obj))
    (and
      (== (count obj) (first dims))
      (every? (partial check-dims-equality (rest dims)) obj))))

(defn correct-dims?
  ([args dims]
   (every? (partial check-dims-equality dims) args))
  ([args]
   (correct-dims? args (get-dim (first args)))))

(defn correct-types? [checker & args] (every? checker args))

(defn basic-checks
  ([checker args]
   (and (apply (partial correct-types? checker) args) (or (== (count args) 1) (correct-dims? args))))
  ([checker sample args]
   (basic-checks checker (cons sample args))))

(defn coordinate-operation [f checker]
  (fn [head & others] {
    :pre[(basic-checks checker head others)]
  } (letfn [(step-into [& args]
    (if (every? number? args)
      (apply f args)
      (apply mapv step-into args)))]
    (apply step-into head others))))


; vectors

(defn check-vector? [vec] (and (vector? vec) (every? number? vec)))

(def v+ (coordinate-operation + check-vector?))
(def v- (coordinate-operation - check-vector?))
(def v* (coordinate-operation * check-vector?))
(def vd (coordinate-operation / check-vector?))

(defn scalar [& args] {
  :pre [(basic-checks check-vector? args)]
} (apply + (apply v* args)))

(defn vect [& args] {
  :pre [(basic-checks check-vector? (vector 0 0 0) args)]
} (reduce
  (fn [a b] (letfn [(calc-coordinate [i j]
    (- (* (a i) (b j)) (* (a j) (b i))))]
  (vector (calc-coordinate 1 2) (calc-coordinate 2 0) (calc-coordinate 0 1)))) args))

(defn v*s [v & args] {
  :pre [(and (basic-checks check-vector? (list v)) (every? number? args))]
} (mapv (apply partial * args) v))


; matrix

(defn check-matrix?
  ([m lvl]
   (if (== lvl 0)
     (number? m)
     (if (== lvl 1)
       (check-vector? m)
       (and (not (number? m)) (every? (fn [layer] (check-matrix? layer (- lvl 1))) m)))))
  ([m]
   (check-matrix? m 2)))


(defn compatible-matrices? [head & others]
  (reduce #(if (== (last (get-dim %1)) (first (get-dim %2))) %2 nil) head others))

(def m+ (coordinate-operation + check-matrix?))
(def m- (coordinate-operation - check-matrix?))
(def m* (coordinate-operation * check-matrix?))
(def md (coordinate-operation / check-matrix?))

(defn transpose [m] {
  :pre [(basic-checks check-matrix? (list m))]
} (apply mapv vector m))

(defn m*s [m & args] {
  :pre [(and (basic-checks check-matrix? (list m)) (every? number? args))]
} (mapv (fn [v] (apply v*s v args)) m))

(defn m*v [m v] {
  :pre [(and (basic-checks check-matrix? (list m)) (== (count (first m)) (count v)))]
} (mapv (partial apply +) (mapv (partial v* v) m)))

(defn m*m [head & others] {
  :pre [(and (apply (partial correct-types? check-matrix? head) others) (apply (partial compatible-matrices? head) others))]
} (reduce (fn [a b] (mapv (partial m*v (transpose b)) a)) head others))


; tensor

(defn check-tensor? [t] (check-matrix? t (count (get-dim t))))

(def t+ (coordinate-operation + check-tensor?))
(def t- (coordinate-operation - check-tensor?))
(def t* (coordinate-operation * check-tensor?))
(def td (coordinate-operation / check-tensor?))


; broadcast

; a with b
(defn compatible-with? [a b] {
  :pre[(and (check-tensor? a) (check-tensor? b))]
} (letfn [(checkPrefix [nxtA nxtB]
    (or
      (empty? nxtA)
      (and (== (first nxtA) (first nxtB)) (recur (rest nxtA) (rest nxtB)))))]
  (and (<= (count (get-dim a)) (count (get-dim b))) (checkPrefix (get-dim a) (get-dim b)))))

; a to b
(defn broadcast [a b] {
  :pre[(and (check-tensor? a) (check-tensor? b) (compatible-with? a b))]
} (letfn [(cast [dimB lvl actualA]
  (if (empty? dimB)
    actualA
    (if (== lvl 0)
      (apply vector (repeat (first dimB) (cast (rest dimB) lvl actualA)))
      (mapv (partial cast (rest dimB) (- lvl 1)) actualA))))]
  (cast (get-dim b) (count (get-dim a)) a)))

(defn compatible-tensors? [& args]
  (letfn [(step-into [current next]
    (or
      (<= (count next) 1)
      (if (compatible-with? current (first next))
        (recur (broadcast current (first next)) (rest next))
        (and
          (compatible-with? (first next) current)
          (recur (broadcast (first next) current) (rest next))))))]
  (step-into (first args) (rest args))))


(defn tb-operation [f] (fn
  ([head & others] {
    :pre[(and (apply (partial correct-types? check-tensor? head) others) (compatible-tensors? head others))]
  } (reduce (fn [a b]
    (if (compatible-with? a b)
      (f (broadcast a b) b)
      (f a (broadcast b a)))
    ) head others))
  ([head] {
    :pre[(check-tensor? head)]
  } (f head))))

(def tb+ (tb-operation t+))
(def tb- (tb-operation t-))
(def tb* (tb-operation t*))
(def tbd (tb-operation td))
