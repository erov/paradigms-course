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

    Immutable(queue):
        queue.n == queue.n' && forall int i in [1, queue.n]: queue.a[i] == queue.a'[i]
 */
public class ArrayQueueADT {
    private int head = 0, size = 0;  // [head, tail = head + size - 1]
    private Object[] arr = new Object[1];

    /*
        Pred: true
        Post: R.n == 0 && R - new
     */
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    /*
        Pred: e != null && queue != null
        Post: queue.n == queue.n'+1 && queue.a[queue.n] == e && forall int i in [1, queue.n']: queue.a[i] == queue.a'[i]
     */
    public static void enqueue(ArrayQueueADT queue, Object e) {
        Objects.requireNonNull(queue, "queue cannot be null to call enqueue(queue, e)");
        Objects.requireNonNull(e, "e cannot be null to call enqueue(queue, e)");
        ensureCapacity(queue, queue.size + 1);
        queue.size++;
        queue.arr[getTail(queue)] = e;
    }

    /*
        Pred: queue != null && queue.n > 0
        Post: R == queue.a[1] && Immutable(queue)
     */
    public static Object element(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call element(queue)");
        assert queue.size > 0 : "queue cannot be empty to call element(queue)";
        return queue.arr[queue.head];
    }

    /*
        Pred: queue != null && queue.n > 0
        Post: R == queue.a[1] && queue.n == queue.n'-1 && forall int i in [1, queue.n]: queue.a[i] == queue.a'[i+1]
     */
    public static Object dequeue(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call dequeue(queue)");
        assert queue.size > 0 : "queue cannot be empty to call dequeue(queue)";
        Object result = element(queue);
        queue.arr[queue.head] = null;
        setHead(queue, queue.head + 1);
        queue.size--;
        return result;
    }

    /*
        Pred: queue != null
        Post: R == queue.n && Immutable(queue)
     */
    public static int size(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call size(queue)");
        return queue.size;
    }

    /*
        Pred: queue != null
        Post: R == (queue.n == 0) && Immutable(queue)
    */
    public static boolean isEmpty(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call isEmpty(queue)");
        return queue.size == 0;
    }

    /*
        Pred: queue != null
        Post: queue.n == 0
     */
    public static void clear(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call clear(queue)");
        Arrays.fill(queue.arr, null);
        queue.head = queue.size = 0;
    }

    /*
        Pred: e != null && queue != null
        Post: queue.n == queue.n'+1 && queue.a[1] == e && forall int i in [1, queue.n']: queue.a[i+1] == queue.a'[i]
    */
    public static void push(ArrayQueueADT queue, Object e) {
        Objects.requireNonNull(queue, "queue cannot be null to call push(queue, e)");
        Objects.requireNonNull(e, "e cannot be null to call push(queue, e)");
        ensureCapacity(queue, queue.size + 1);
        setHead(queue, queue.head - 1);
        queue.size++;
        queue.arr[queue.head] = e;
    }

    /*
        Pred: queue != null && queue.n > 0
        Post: R == queue.a[queue.n] && Immutable
     */
    public static Object peek(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call peek(queue)");
        assert queue.size > 0 : "queue cannot be empty to call peek(queue)";
        return queue.arr[getTail(queue)];
    }

    /*
        Pred: queue != null && queue.n > 0
        Post: R == queue.a[queue.n'] && queue.n == queue.n'-1 && forall int i in [1, queue.n]: aqueue.[i] == queue.a'[i]
     */
    public static Object remove(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call remove(queue)");
        assert queue.size > 0 : "queue cannot be empty to call remove(queue)";
        Object result = peek(queue);
        queue.arr[getTail(queue)] = null;
        queue.size--;
        return result;
    }

    /*
        Pred: queue != null
        Post: R - Object array && R.length == queue.n && forall int i in [1, queue.n]: R[i] == queue.a[i] && Immutable(queue)
     */
    public static Object[] toArray(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call toArray(queue)");
        Object[] result = new Object[queue.size];
        int tail = getTail(queue);
        if (queue.head <= tail || queue.size == 0) {
            System.arraycopy(queue.arr, queue.head, result, 0, queue.size);
        } else {
            System.arraycopy(queue.arr, queue.head, result, 0, queue.arr.length - queue.head);
            System.arraycopy(queue.arr, 0, result, queue.arr.length - queue.head, tail + 1);
        }
        return result;
    }

    /*
        Pred: queue != null
        Post: R == "[(String)queue.a[1], (String)queue.a[2], ..., (String)queue.a[n]]" && Immutable(queue)
     */
    public static String toStr(ArrayQueueADT queue) {
        Objects.requireNonNull(queue, "queue cannot be null to call toStr(queue)");
        return Arrays.toString(toArray(queue));
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.arr.length) {
            queue.arr = Arrays.copyOf(toArray(queue), capacity * 2);
            queue.head = 0;
        }
    }

    private static int actualIndex(ArrayQueueADT queue, int index) {
        return (index + queue.arr.length) % queue.arr.length;
    }

    private static int getTail(ArrayQueueADT queue) {
        return actualIndex(queue, queue.head + queue.size - 1);
    }

    private static void setHead(ArrayQueueADT queue, int value) {
        queue.head = actualIndex(queue, value);
    }
}
