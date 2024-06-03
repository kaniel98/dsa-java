package DataStructures.stack;

public class StackLinkedList {
    private Node top;
    private int height;

    // Constructor for DataStructures.stack class
    public StackLinkedList(int value) {
        Node newNode = new Node(value);
        top = newNode;
        height = 1;
    }

    // Helper functions
    public void printStack() {
        Node temp = top;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void getTop() {
        System.out.println("Top " + top.value);
    }

    public void getHeight() {
        System.out.println("Height " + height);
    }

    //  Additional methods
    public void push(int value) {
        Node newNode = new Node(value);
        // Edge case: height is 0
        if (this.height > 0) {
            newNode.next = this.top;
        }
        this.top = newNode;
        this.height++;
    }

    public Node pop() {
        if (this.height == 0) return null;

        Node temp = this.top;
        this.top = top.next;
        temp.next = null;
        this.height--;
        return temp;
    }

    // Constructor for Node in a DataStructures.stack
    class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
