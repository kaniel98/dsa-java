package Stack;

import java.util.ArrayList;

public class StackArrayList<T> {
    // Generic array list
    private ArrayList<T> stack;

    // Constructor
    public StackArrayList() {
        this.stack = new ArrayList<T>();
    }

    // Push
    public void push(T value) {
        this.stack.add(value);
    }

    // Pop
    public T pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

}
