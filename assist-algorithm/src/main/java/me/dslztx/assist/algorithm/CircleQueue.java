package me.dslztx.assist.algorithm;

/**
 * 循环队列
 */
public class CircleQueue<T> {

    private Object[] queue;

    /**
     * 指向队列首元素，如果不存在则为-1
     */
    private int head = -1;

    /**
     * 指向下一个元素放置位置
     */
    private int tail = 0;

    private int capacity = 0;

    public CircleQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("queue size less than 1");
        }

        this.capacity = capacity;
        this.queue = new Object[capacity];
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public boolean isFull() {
        return head == tail;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isNotFull() {
        return !isFull();
    }

    public void push(T element) {
        if (isFull()) {
            throw new RuntimeException("queue is full");
        }
        if (head == -1) {
            head = tail;
        }

        queue[tail] = element;
        tail = (tail + 1) % capacity;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty");
        }

        T result = (T)queue[head];

        if ((head + 1) % capacity == tail) {
            head = -1;
        } else {
            head = (head + 1) % capacity;
        }

        return result;
    }

    public T top() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty");
        }

        return (T)queue[head];
    }

}
