package util;

public class StackADT<T> {
    private ArrayListADT<T> list = new ArrayListADT<>();

    public void push(T item) {
        list.add(item);
    }

    public T pop() {
        if (list.size() == 0) {
            return null;
        }
        return list.remove(list.size() - 1);
    }

    public T peek() {
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public T get(int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    public void set(int index, T element) {
        if (index >= 0 && index < list.size()) {
            list.set(index, element);
        }
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int size() {
        return list.size();
    }

    public void add(T item) {
        push(item);
    }

    public T remove(int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.remove(index);
    }
}