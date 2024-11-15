package util;

import java.util.LinkedList;

public class Queue<T> {
    private LinkedList<T> list = new LinkedList<>();

    // Add an item to the queue
    public void enqueue(T item) {
        list.addLast(item);
    }

    // Remove an item from the queue
    public T dequeue() {
        return list.removeFirst();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return list.size();
    }

    // Convert the queue to an array
    public T[] toArray(T[] a) {
        return list.toArray(a);
    }
}