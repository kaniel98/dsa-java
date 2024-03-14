package queue;

public class Queue {
    private Node first;
    private Node last;
    private int length;

    public Queue(int value) {
        Node newNode = new Node(value);
        this.first = newNode;
        this.last = newNode;
        length = 1;
    }

    public void printQueue() {
        Node temp = first;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void getLength() {
        System.out.println("Length: " + length);
    }

    public void getFirst() {
        System.out.println("First: " + first.value);
    }

    public void getLast() {
        System.out.println("Last: " + last.value);
    }

    public void enqueue(int value) {
        Node newNode = new Node(value);
        // Check if empty
        if (length == 0) {
            this.first = newNode;
            this.last = newNode;
        } else {
            // Set this current's next to be the new node
            this.last.next = newNode;
            // Set the last node to be the new node.
            this.last = newNode;
        }

        // Increase the length
        this.length++;
    }

    public Node dequeue() {
        // Remove the first item in the queue

        // Edge case 1: No item in the queue
        if (this.length == 0) return null;
        // Edge case 2: One item in the queue
        Node temp = first;
        if (this.length == 1) {
            this.first = null;
            this.last = null;
        } else {
            this.first = this.first.next;
            temp.next = null;
        }
        this.length--;
        return temp;
    }

    // Constructor for Node in a Queue
    class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

}
