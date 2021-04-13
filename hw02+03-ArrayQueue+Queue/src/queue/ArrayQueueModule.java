package queue;

import java.util.Arrays;
import java.util.Objects;

/*
    Model:
        [a_1, a_2, .., a_n]
        n - queue size

    Inv:
        n >= 0
        forall int i in [1, n]: a[i] != null

    Immutable:
        n == n' && forall int i in [1, n]: a[i] == a'[i]
 */
public class ArrayQueueModule {
    private static int head = 0, size = 0;  // [head, tail = head + size - 1]
    private static Object[] arr = new Object[1];

    /*
        Pred: e != null
        Post: n == n'+1 && a[n] == e && forall int i in [1, n']: a[i] == a'[i]
     */
    public static void enqueue(Object e) {
        Objects.requireNonNull(e, "e cannot be null to call enqueue(e)");
        ensureCapacity(size + 1);
        size++;
        arr[getTail()] = e;
    }

    /*
        Pred: n > 0
        Post: R == a[1] && Immutable
     */
    public static Object element() {
        assert size > 0 : "queue cannot be empty to call element()";
        return arr[head];
    }

    /*
        Pred: n > 0
        Post: R == a[1] && n == n'-1 && forall int i in [1, n]: a[i] == a'[i+1]
     */
    public static Object dequeue() {
        assert size > 0 : "queue cannot be empty to call dequeue()";
        Object result = element();
        arr[head] = null;
        setHead(head + 1);
        size--;
        return result;
    }

    /*
        Pred: true
        Post: R == n && Immutable
     */
    public static int size() {
        return size;
    }

    /*
        Pred: true
        Post: R == (n == 0) && Immutable
    */
    public static boolean isEmpty() {
        return size == 0;
    }

    /*
        Pred: true
        Post: n == 0
     */
    public static void clear() {
        Arrays.fill(arr, null);
        head = size = 0;
    }

    /*
        Pred: e != null
        Post: n == n'+1 && a[1] == e && forall int i in [1, n']: a[i+1] == a'[i]
    */
    public static void push(Object e) {
        Objects.requireNonNull(e, "e cannot be null to call push(e)");
        ensureCapacity(size + 1);
        setHead(head - 1);
        size++;
        arr[head] = e;
    }

    /*
        Pred: n > 0
        Post: R == a[n] && Immutable
     */
    public static Object peek() {
        assert size > 0 : "queue cannot be empty to call peek()";
        return arr[getTail()];
    }

    /*
        Pred: n > 0
        Post: R == a[n'] && n == n'-1 && forall int i in [1, n]: a[i] == a'[i]
     */
    public static Object remove() {
        assert size > 0 : "queue cannot be empty to call remove()";
        Object result = peek();
        arr[getTail()] = null;
        size--;
        return result;
    }

    /*
        Pred: true
        Post: R - Object array && R.length == n && forall int i in [1, n]: R[i] == a[i] && Immutable
     */
    public static Object[] toArray() {
        Object[] result = new Object[size];
        int tail = getTail();
        if (head <= tail || size == 0) {
            System.arraycopy(arr, head, result, 0, size);
        } else {
            System.arraycopy(arr, head, result, 0, arr.length - head);
            System.arraycopy(arr, 0, result, arr.length - head, tail + 1);
        }
        return result;
    }

    /*
        Pred: true
        Post: R == "[(String)a[1], (String)a[2], ..., (String)a[n]]" && Immutable
     */
    public static String toStr() {
        return Arrays.toString(toArray());
    }

    private static void ensureCapacity(int capacity) {
        if (capacity > arr.length) {
            arr = Arrays.copyOf(toArray(), capacity * 2);
            head = 0;
        }
    }

    private static int actualIndex(int index) {
        return (index + arr.length) % arr.length;
    }

    private static int getTail() {
        return actualIndex(head + size - 1);
    }

    private static void setHead(int value) {
        head = actualIndex(value);
    }
}
