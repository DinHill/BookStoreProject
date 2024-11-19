package util;

public class QueueADT<T> {
    private LinkedListADT<T> list = new LinkedListADT<>();

    public void enqueue(T item) {
        list.add(item);
    }

    public T dequeue() {
        return list.remove(0);
    }

    public T get(int index) {
        return list.get(index);
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int size() {
        return list.size();
    }
}