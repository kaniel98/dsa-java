package DoubleLinkedLists;

public class DoubleLinkedList {

    class Node {
        int value;
        // Main difference: Additional field to store the pointer to the previous node
        Node prev;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int length;

    // Constructor for Double Linked lists
    public DoubleLinkedList(int value) {
        Node newNode = new Node(value);
        head = newNode;
        tail = newNode;
        length = 1;
    }

    public void getHead() {
        System.out.println("Head: " + head.value);
    }

    public void getTail() {
        System.out.println("Tail: " + tail.value);
    }

    public void getLength() {
        System.out.println("Length: " + length);
    }

    public void printList() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void append(int value) {
        // Additional consideration - Need to point "prev" back
        // Edge case: when there is an empty double linked list
        Node newNode = new Node(value);
        if (this.length == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        this.length++;
    }

    public Node removeLast() {
        // Compared to single linked list, there is no need to iterate and find the last node
        // There are two pointers going back & forth
        // Edge case: if length == 0
        if (length == 0) return null;
        Node temp = tail;
        if (length == 1) {
            // Edge case: if length == 1
            this.head = null;
            this.tail = null;
        } else {
            // Else execute as usual
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        this.length--;
        return temp;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (this.length == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.head.prev = newNode;
            newNode.next = this.head;
            this.head = newNode;
        }
        this.length++;
    }
}
