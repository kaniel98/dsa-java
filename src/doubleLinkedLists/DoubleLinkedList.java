package doubleLinkedLists;

public class DoubleLinkedList {

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
        if (this.length == 0) return null;
        Node temp = tail;
        if (this.length == 1) {
            // Edge case: if length == 1
            this.head = null;
            this.tail = null;
        } else {
            // Else execute as usual
            this.tail = this.tail.prev;
            this.tail.next = null;
            temp.prev = null;
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

    public Node removeFirst() {
        // Edge case 1: if length == 0
        if (this.length == 0) return null;
        Node temp = this.head;
        if (this.length == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = temp.next;
            this.head.prev = null;
            temp.next = null;
        }
        this.length--;
        return temp;
    }

    // Different implementation compared to Single Linked Lists
    public Node get(int index) {
        // Cannot get an index that is less than 0 or more than the current length
        if (index < 0 || index >= this.length) return null;

        Node temp = head;
        // Unlike Single Linked List, Double Linked list allows us to start from the back
        if (index < this.length / 2) {
            // If node is in the first half of the linked list, we will do a forward loop
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            // Else start from the tail instead;
            temp = tail;
            for (int i = this.length - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    public boolean set(int index, int value) {
        // Cannot get an index that is less than 0 or more than the current length
        Node temp = get(index);
        if (temp != null) {
            temp.value = value;
            return true;
        }
        return false;
    }

    public boolean insert(int index, int value) {
        // Check if index is within range
        if (index < 0 || index > this.length) return false;

        // Check if index is either at the start or at the end:
        if (index == 0) {
            prepend(value);
            return true;
        }

        if (index == this.length) {
            append(value);
            return true;
        }

        // Else proceed to create
        Node before = get(index - 1);
        Node after = before.next; // Preferred to get before compared to after (More efficient & robust)

        Node newNode = new Node(value);
        before.next = newNode;
        newNode.prev = before;
        after.prev = newNode;
        newNode.next = after;
        length++;
        return true;
    }

    public Node remove(int index) {
        // Check if index is in range
        if (index < 0 || index >= this.length) return null;

        // If at the start or at the end, use the respective methods;
        if (index == 0) return removeFirst();
        if (index == this.length - 1) return removeLast();

        // Removing the item in the middle of the list
        Node temp = get(index);
        // Assigning the respective variables
        Node before = temp.prev;
        Node after = temp.next;
        before.next = after;
        after.prev = before;

        // Short way of doing this can be:
        this.length--;
        return temp;
    }

    public boolean swapFirstLast() {
        if (this.length < 2) return false;

        // Swapping the values
        int temp = this.tail.value;
        this.tail.value = this.head.value;
        this.head.value = temp;

        return true;
    }

    public void reverse() {
        // Edge case: if length == 0 or 1
        if (this.length == 0 || this.length == 1) return;

        // Start reversing from the tail
        Node current = this.head;
        // Temp holder
        Node temp = null;
        while (current != null) {
            // Temp will be pointing to previous
            temp = current.prev;
            // Switch the current's previous with its next
            current.prev = current.next;
            // Switch the current's next with its previous
            current.next = temp;
            // Move current to be its "Previous" which should be its next once reversed
            current = current.prev;
        }

        // Switch the header by setting temp to be temporarily head
        temp = this.head;
        // Switch head to become tail
        head = tail;
        // Switch tail to become temp which should be pointing to the previous head
        tail = temp;
    }

    public boolean isPalindrome() {

        // Edge case - 0 or 1
        if (this.length == 0 || this.length == 1) return true;

        int len = this.length % 2 == 0 ? this.length / 2 : (this.length / 2) + 1;
        Node start = this.head;
        Node end = this.tail;

        // Iterate from the first half and second half
        for (int i = 0; i < len; i++) {
            // If it matches, pass
            // If it doesnt match, return false
            if (start.value != end.value) {
                return false;
            }
            start = start.next;
            end = end.prev;
        }
        return true;
    }

    class Node {
        int value;
        // Main difference: Additional field to store the pointer to the previous node
        Node prev;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
