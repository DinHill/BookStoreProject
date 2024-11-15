package util;

import java.util.LinkedList;

public class Stack<T> {
    private LinkedList<T> list = new LinkedList<>();

    // Add an item to the stack
    public void push(T item) {
        list.addFirst(item);
    }

    // Remove an item from the stack
    public T pop() {
        return list.removeFirst();
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Get the size of the stack
    public int size() {
        return list.size();
    }
}