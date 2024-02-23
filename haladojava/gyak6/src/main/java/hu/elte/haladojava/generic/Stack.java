package hu.elte.haladojava.generic;

public class Stack<T> {
    private final T[] elements;
    private final int capacity;
    private int pointer = - 1;

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity cannot be negative: " + capacity);
        }
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
    }

    public void push(T element) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException("the stack is full, element cannot be pushed");
        }
        elements[++pointer] = element;
    }

    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("the stack is empty, there is no element to pop");
        }
        return elements[pointer--];
    }

    public int size() {
        return pointer + 1;
    }

    public boolean isEmpty() {
        return pointer == -1;
    }

    public boolean isFull() {
        return pointer == capacity - 1;
    }
}