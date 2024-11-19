package util;

public class NavigationStack {
    private StackADT<String> stack = new StackADT<>();

    public void pushPage(String page) {
        stack.push(page);
    }

    public String popPage() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public String peekPage() {
        return stack.isEmpty() ? null : stack.peek();
    }
}