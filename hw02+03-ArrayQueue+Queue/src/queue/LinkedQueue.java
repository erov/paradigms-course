package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    private static class Node {
        private final Object value;
        private Node next;

        private Node(Object value) {
            this.value = value;
        }
    }

    @Override
    protected Queue create() {
        return new LinkedQueue();
    }

    @Override
    protected void enqueueImpl(Object e) {
        Node newTail = new Node(e);
        if (size == 0) {
            head = tail = newTail;
        } else {
            tail.next = newTail;
            tail = newTail;
        }
    }

    @Override
    protected Object getFront(boolean removeFirst) {
        Object result = head.value;
        if (removeFirst) {
            head = head.next;
            if (size == 1) {
                tail = head;
            }
            size--;
        }
        return result;
    }
}
