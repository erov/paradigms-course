package queue;

import java.util.Arrays;
import java.util.Objects;

/*
    Immutable:
        n == n' && forall int i in [1, n]: a[i] == a'[i]
 */
public class ArrayQueue extends AbstractQueue {
    private int head = 0;  // [head, tail = head + size - 1]
    private Object[] arr = new Object[1];

    @Override
    protected Queue create() {
        return new ArrayQueue();
    }

    @Override
    protected void enqueueImpl(Object e) {
        ensureCapacity(size + 1);
        arr[actualIndex(head + size)] = e;
    }

    @Override
    protected Object getFront(boolean remove) {
        Object result = arr[head];
        if (remove) {
            setHead(head + 1);
            size--;
        }
        return result;
    }

    /*
        Pred: e != null
        Post: n == n'+1 && a[1] == e && forall int i in [1, n']: a[i+1] == a'[i]
    */
    public void push(Object e) {
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
    public Object peek() {
        assert size > 0 : "queue cannot be empty to call peek()";
        return arr[getTail()];
    }

    /*
        Pred: n > 0
        Post: R == a[n'] && n == n'-1 && forall int i in [1, n]: a[i] == a'[i]
     */
    public Object remove() {
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
    public Object[] toArray() {
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
    public String toStr() {
        return Arrays.toString(toArray());
    }

    private void ensureCapacity(int capacity) {
        if (capacity > arr.length) {
            arr = Arrays.copyOf(toArray(), capacity * 2);
            head = 0;
        }
    }

    private int actualIndex(int index) {
        return (index + arr.length) % arr.length;
    }

    private int getTail() {
        return actualIndex(head + size - 1);
    }

    private void setHead(int value) {
        head = actualIndex(value);
    }
}
