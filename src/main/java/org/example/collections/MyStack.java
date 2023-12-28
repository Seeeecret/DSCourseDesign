package org.example.collections;

public class MyStack<T> {
    private Object[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public MyStack() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void push(T element) {
        ensureCapacity();
        array[size++] = element;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        T element = (T) array[--size];
        array[size] = null;
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return (T) array[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            array = java.util.Arrays.copyOf(array, newCapacity);
        }
    }

    public int size() {
        return this.size;
    }
}
