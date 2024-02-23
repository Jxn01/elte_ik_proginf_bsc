package org.example;
import java.util.ArrayList;
import java.util.List;

public class ArrayStack<T> implements Stack<T> {

    private final List<T> elements = new ArrayList<>();

    @Override
    public void push(T element) {
        // no need for checking whether we have enough space in the underlying list, it will resize itself
        elements.add(element);
    }

    @Override
    public T pop() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("element cannot be popped from an empty stack");
        }
        T top = top();
        elements.remove(elements.size() - 1);
        return top;
    }
    
    @Override
    public T top() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("there is no top element on an empty stack");
        }
        return elements.get(elements.size() - 1);
    }
    
    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

}