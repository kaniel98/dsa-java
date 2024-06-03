package DataStructures.linkedList;

public class LinkedList {
    private Node head;
    private Node tail;
    private int length;

    public LinkedList(int value) {
        Node startingNode = new Node(value);
        // Both head and tail will be pointing to the same node
        this.head = startingNode;
        this.tail = startingNode;
        // Starting length of the linked list is 1
        this.length = 1;
    }

    // Time complexity - O(1)
    public void append(int value) {
        // Create a new node
        Node newNode = new Node(value);

        // Check if linked list is empty
        if (this.length == 0) {
            // Initialize head and tail to the new node
            this.head = newNode;
            this.tail = newNode;
        }
        // Else, proceed to append it to the end
        else {
            // Set the current tail's next node to the new node
            this.tail.next = newNode;
            // Set the tail to the new node
            this.tail = newNode;
        }

        // Lastly, increase length by one
        this.length++;
    }

    // Time complexity - O(1)
    public void prepend(int value) {
        Node newNode = new Node(value);
        // Edge case 1: If the linked list is empty
        if (this.length == 0) {
            this.head = newNode;
            this.tail = newNode;
        }
        // Common case: if the linked list is more than one
        else {
            // New node's next will be the current head
            newNode.next = this.head;
            // Point the current head to the new node
            this.head = newNode;
        }
        this.length++;
    }

    // Time complexity - O(N)
    public Node removeLastItem() {
        // Edge case 1: If the linked list is empty
        if (this.length == 0) return null;

        Node temp = this.head;
        Node pre = this.head;
        while (temp.next != null) {
            pre = temp;
            temp = temp.next;
        }

        // At the end, temp will be at the original tail while pre is the node before the tail
        this.tail = pre;
        this.tail.next = null;
        this.length--;

        if (length == 0) {
            this.head = null;
            this.tail = null;
        }
        return temp;
    }

    // Time complexity - O(1)
    public Node removeFirstItem() {
        // Edge case 1: If the linked list is empty
        if (this.length == 0) return null;

        Node temp = this.head;
        this.head = temp.next;
        this.length--;

        if (length == 0) {
            this.tail = null;
        }
        return temp;
    }

    // Time complexity - O(N)
    public Boolean insert(int index, int value) {
        // Checking for edge cases
        if (index < 0 || index > length) return false;

        if (index == 0) {
            prepend(value);
            this.length++;
            return true;
        }

        if (index == length - 1) {
            append(value);
            this.length++;
            return true;
        }

        // Else get the node before where the insert is to happen
        // This node's value will be before the new node, and its next will be the new node
        // THe new node's next will be the previous node's next
        Node temp = get(index - 1);
        if (temp != null) {
            Node newNode = new Node(value);
            newNode.next = temp.next;
            temp.next = newNode;
            this.length++;
            return true;
        }

        return false;
    }

    // Time complexity - O(N)
    public Node remove(int index) {
        if (index < 0 || index > length) return null;

        if (index == 0) return removeFirstItem();

        if (index == length - 1) return removeLastItem();

        Node prev = get(index - 1);
        Node temp = prev.next;

        prev.next = temp.next;
        temp.next = null;
        length--;
        return temp;
    }

    // Time complexity - O(N)
    public Node get(int index) {
        // If requested index is more than length or less thn 0, return null
        // Ensures that the index is within range
        if (index < 0 || index >= length) {
            return null;
        }

        Node currNode = this.head;
        for (int i = 0; i < index; i++) {
            currNode = currNode.next;
        }
        return currNode;
    }

    // Time complexity - O(N)
    public Boolean set(int index, int value) {
        Node targetNode = get(index);
        if (targetNode != null) {
            targetNode.val = value;
            return true;
        }
        return false;
    }

    // Time complexity - O(N)
    public void reverse() {
        // First set head and tail
        Node temp = this.head;
        this.head = this.tail;
        this.tail = temp;

        // Next proceed to set the individual nodes to be reversed
        Node after = temp.next;
        Node before = null;

        for (int i = 0; i < length; i++) {
            after = temp.next;
            // Flips the arrow of the current node to point to the node before
            temp.next = before;
            // Move temp to be the next node
            before = temp;
            // Moving temp to the next node again
            temp = after;
        }
    }

    public void getHead() {
        System.out.println("Head: " + head.val);
    }

    public void getTail() {
        System.out.println("Tail: " + tail.val);
    }

    public void getLength() {
        System.out.println("Length: " + length);
    }

    public void printList() {
        while (this.head != null) {
            System.out.println(this.head.val);
            this.head = this.head.next;
        }
        System.out.println();
    }

    // Inner node class for LinkedList, (Also known as Nested class)
    public class Node {
        public int val;
        public Node next;

        public Node(int value) {
            this.val = value;
        }
    }

}
