; FunctionalExpression

(def constant constantly)

(defn variable [name] (fn [vars] (vars name)))

(defn abstract-operation [f]
  (fn [& args] (fn [vars] (apply f (mapv #(% vars) args)))))

(def add (abstract-operation +))
(def subtract (abstract-operation -))
(def multiply (abstract-operation *))

(defn calculate-divide
  ([head & others] (/ head (double (apply * others))))
  ([arg] (/ 1 (double arg))))

(def calculate-negate #(- %))

(def divide (abstract-operation calculate-divide))
(def negate (abstract-operation calculate-negate))

(defn square [a] (* a a))
(defn calculate-mean [& args] (/ (apply + args) (count args)))
(defn calculate-varn [& args] (- (apply calculate-mean (mapv square args)) (square (apply calculate-mean args))))

(def mean (abstract-operation calculate-mean))
(def varn (abstract-operation calculate-varn))

(def FUNCTION-OPERATORS {
  '+ add
  '- subtract
  '* multiply
  '/ divide
  'negate negate
  'mean mean
  'varn varn
})

(def FUNCTION-VARIABLES {
  'x (variable "x")
  'y (variable "y")
  'z (variable "z")
})

(defn parseAbstract [input OPERATIONS VARIABLES CONSTANT]
  (letfn [(parse [expr]
    (cond
      (number? expr) (CONSTANT expr)
      (symbol? expr) (VARIABLES expr)
      :else (apply (OPERATIONS (first expr)) (mapv parse (rest expr)))))]
  (parse (read-string input))))

(defn parseFunction [input]
  (parseAbstract input FUNCTION-OPERATORS FUNCTION-VARIABLES constant))



; ObjectExpression

(load-file "proto.clj")

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))
(def toStringInfix (method :toStringInfix))

(defn create-expression-prototype [evaluate toString diff toStringInfix]
  {:evaluate evaluate, :toString toString, :diff diff, :toStringInfix toStringInfix})

(declare Constant-ZERO)
(def Constant
  (let [_value (field :value)]
    (constructor
      (fn [this value]
        (assoc this :value value))
      (create-expression-prototype
        (fn [this _]
          (_value this))
        (fn [this]
          (str (_value this)))
        (fn [_ _]
          Constant-ZERO)
        toString))))

(def Constant-ZERO (Constant 0.0))
(def Constant-ONE (Constant 1.0))

(def Variable
  (let [_name (field :name)]
    (constructor
      (fn [this name]
        (assoc this :name name))
      (create-expression-prototype
        (fn [this vars]
          (vars (clojure.string/lower-case (str (first (_name this))))))
        (fn [this]
          (_name this))
        (fn [this dv]
          (if (= (_name this) dv)
            Constant-ONE
            Constant-ZERO))
        toString))))

(def AbstractOperation
  (let [_args (field :args)
        _operation (field :operation)
        _operator (field :operator)
        _diffRule (field :diffRule)]
    (create-expression-prototype
      (fn [this vars]
        (apply (_operation this) (mapv #(evaluate % vars) (_args this))))
      (fn [this]
        (str "(" (_operator this) " " (clojure.string/join " " (mapv #(toString %) (_args this))) ")"))
      (fn [this dv]
        ((_diffRule this) (_args this) (mapv #(diff % dv) (_args this))))
      (fn [this]
        (if (== (count (_args this)) 2)
          (str "(" (toStringInfix (first (_args this))) " " (_operator this) " " (toStringInfix (second (_args this))) ")")
          (str (_operator this) "(" (toStringInfix (first (_args this))) ")"))))))

(defn create-new-operation [operation operator diffRule]
  (constructor
    (fn [this & args] (assoc this :args args))
    {:prototype AbstractOperation
     :operation operation
     :operator operator
     :diffRule diffRule}))

(def Negate
  (create-new-operation calculate-negate 'negate
    (fn [args dargs]
      (Negate (first dargs)))))

(def Add
  (create-new-operation + '+
    (fn [args dargs]
      (apply Add dargs))))

(def Subtract
  (create-new-operation - '-
    (fn [args dargs]
      (apply Subtract dargs))))

(declare diff-multiply)

(def Multiply
  (create-new-operation * '*
    (fn [args dargs]
      (diff-multiply args dargs))))

(defn diff-repeat [args dargs Operation diffRule]
  (last (reduce
    (fn [[a da] [b db]]
      (vector (Operation a b) (diffRule a da b db)))
    (mapv vector args dargs))))

(defn diff-multiply [args dargs]
  (diff-repeat args dargs Multiply #(Add (Multiply %1 %4) (Multiply %3 %2))))

(def Divide
  (create-new-operation calculate-divide '/
    (fn [[f & args] [df & dargs]]
      (if (empty? args)
        (Negate (Divide df (Multiply f f)))
        (diff-repeat (cons f args) (cons df dargs) Divide
          #(Divide (Subtract (Multiply %2 %3) (Multiply %1 %4)) (Multiply %3 %3)))))))

(def ArithMean
  (create-new-operation calculate-mean 'arith-mean
    (fn [args dargs]
      (Multiply (Constant (/ 1 (count args))) (apply Add dargs)))))

(defn calculate-geom-mean [& args]
  (Math/pow (Math/abs (double (apply * args))) (/ 1 (count args))))

(def Sign
  (create-new-operation #(Math/signum (double %)) 'sign
    (fn [_] Constant-ZERO)))

(def GeomMean
  (create-new-operation calculate-geom-mean 'geom-mean
    (fn [args dargs]
      (let [n (count args)]
        (Divide
          (Multiply (Sign (apply Multiply args))
            (diff-multiply args dargs))
        (Multiply
          (Constant n)
          (apply Multiply (repeat (dec n) (apply GeomMean args)))))))))

(defn calculate-harm-mean [& args]
  (/ (count args) (double (apply + (mapv #(/ 1 (double %)) args)))))

(def HarmMean
  (create-new-operation calculate-harm-mean 'harm-mean
    (fn [args dargs]
      (let [inv (mapv #(Divide Constant-ONE %) args)
            denominator (Divide Constant-ONE (apply Add inv))]
        (Multiply (Constant (count args)) denominator denominator
                  (apply Add (mapv #(Multiply %1 %1 %2) inv dargs)))))))

(defn calculate-boolean [op]
  (fn [a b] ({false 0 true 1} (op (pos? a) (pos? b)))))

(def calculate-xor #(or (and (not %1) %2) (and %1 (not %2))))

(def And
  (create-new-operation (calculate-boolean #(and %1 %2)) '&& nil))
(def Or
  (create-new-operation (calculate-boolean #(or %1 %2)) '|| nil))
(def Xor
  (create-new-operation (calculate-boolean calculate-xor) (symbol "^^") nil))
(def Impl
  (create-new-operation (calculate-boolean #(or (not %1) %2)) '-> nil))
(def Iff
  (create-new-operation (calculate-boolean (comp not calculate-xor)) '<-> nil))

(def OBJECT-OPERATIONS {
  '+ Add
  '- Subtract
  '* Multiply
  '/ Divide
  'negate Negate
  'arith-mean ArithMean
  'geom-mean GeomMean
  'harm-mean HarmMean
  '&& And
  '|| Or
  (symbol "^^") Xor
  '-> Impl
  '<-> Iff
})

(def OBJECT-VARIABLES {
  'x (Variable "x")
  'y (Variable "y")
  'z (Variable "z")
})

(defn parseObject [input]
  (parseAbstract input OBJECT-OPERATIONS OBJECT-VARIABLES Constant))



; CombinatorParser

(load-file "parser.clj")

(def *space (+char (apply str (filter #(Character/isWhitespace (char %)) (mapv char (range 0 128))))))
(def *ws (+ignore (+star *space)))
(def *digit (+char "1234567890"))
(def *number (+seqf #(read-string (apply str (flatten %&)))
  (+opt (+char "+-")) (+plus *digit) (+opt (+char ".")) (+star *digit)))
(defn *operator [name] (apply +seqf (comp symbol str) (mapv #(+char (str %)) name)))
(defn *operators [ops] (apply +or (mapv *operator ops)))

(def *Constant (+map (comp Constant double) *number))
(def *Variable (+map (comp Variable (partial apply str)) (+plus (+char "xyzXYZ"))))
(defn *Operation [assoc head & tail]
    (let [make-op (fn [a [op b]] (apply (OBJECT-OPERATIONS op) (if (= assoc :left-assoc) `[~a ~b] `[~b ~a])))
          actual-args (if (= assoc :left-assoc)
                        [head tail]
                        (let [[h & t] ((comp reverse flatten cons) head tail)] [h (partition 2 2 t)]))]
      (apply reduce make-op actual-args)))         ; head tail - arg [op arg] [op arg] ..

(declare *infix)
(declare *unary-ops)

(def *highest-priority
  (delay
    (+or
      *Constant
      *Variable
      (+seqf #((OBJECT-OPERATIONS %1) %2) (*operators *unary-ops) *ws *highest-priority)
      (+seqn 1 (+char "(") *ws *infix *ws (+char ")")))))


(defn *next-priority [assoc *higher-priority *operations]
    (+seqf (partial apply *Operation assoc)
         *ws *higher-priority (+star (+seq *ws *operations *ws *higher-priority))))

(def *infix
  (reduce
    (fn [*higher-priority [assoc & ops]]
      (*next-priority assoc *higher-priority (*operators ops)))
    *highest-priority
    [[:left-assoc "*" "/"]
     [:left-assoc "+" "-"]
     [:left-assoc "&&"]
     [:left-assoc "||"]
     [:left-assoc "^^"]
     [:right-assoc "->"]
     [:left-assoc "<->"]
     ]))

(def *unary-ops ["negate"])

(def parseObjectInfix
  (+parser (+seqn 0 *ws *infix *ws)))
