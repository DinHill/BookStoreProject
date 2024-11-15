package util;

import java.util.Stack;

public class NavigationStack {
    private Stack<String> stack = new Stack<>();

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