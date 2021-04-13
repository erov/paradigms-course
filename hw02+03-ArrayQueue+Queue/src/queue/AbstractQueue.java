package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    @Override
    public void enqueue(Object e) {
        Objects.requireNonNull(e, "e cannot be null to call enqueue(e)");
        enqueueImpl(e);
        size++;
    }

    @Override
    public Object element() {
        assert size > 0 : "queue cannot be empty to call element()";
        return getFront(false);
    }

    @Override
    public Object dequeue() {
        assert size > 0 : "queue cannot be empty to call dequeue()";
        return getFront(true);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Queue getNth(int N) {
        assert N > 0 : "N cannot be lower than 1 to call getNth(N)";
        return calculateNth(N, true, false);
    }

    @Override
    public Queue removeNth(int N) {
        assert N > 0 : "N cannot be lower than 1 to call removeNth(N)";
        return calculateNth(N, true, true);
    }

    @Override
    public void dropNth(int N) {
        assert N > 0 : "N cannot be lower than 1 to call dropNth(N)";
        calculateNth(N, false, true);
    }

    private Queue calculateNth(int N, final boolean returnNth, final boolean removeNth) {
        Queue queueWithNth = create();
        int cnt = 1, srcSize = size;
        while (cnt <= srcSize) {
            Object front = dequeue();
            if (cnt++ % N == 0) {
                if (returnNth) {
                    queueWithNth.enqueue(front);
                }
                if (removeNth) {
                    continue;
                }
            }
            enqueue(front);
        }
        return queueWithNth;
    }

    abstract Queue create();

    abstract void enqueueImpl(Object e);

    abstract Object getFront(boolean remove);
}
