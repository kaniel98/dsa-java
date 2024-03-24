package linkedList;

public class LinkedListQuestions {
    public static void main(String[] args) {

    }

    public static class ListNode<T> {
        public T val;
        public ListNode<T> next;

        public ListNode(T val) {
            this(val, null);
        }

        public ListNode(T val, ListNode<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode<Integer> nextNode(ListNode<Integer> node) {
        if (node.next == null) {
            return node;
        }
        return node.next;
    }

    // * Reversing a linked list
    // Time complexity: o(n) - Size of the linked list
    // Space complexity: o(1) - Only need to maintain three instance of node at most
    public ListNode<Integer> reverseLinkedList(ListNode<Integer> head) {
        ListNode<Integer> temp = head;
        ListNode<Integer> before = null;
        ListNode<Integer> after = null;

        // Go down the linked list while temp is not null
        while (temp.val != null) {
            after = temp.next;
            temp.next = before;
            before = temp;
            temp = after;
        }
        return before; // Before would now be the new head;
    }

    // * Merge two sorted list
    // Assuming we can create a new list without any concern about memory
    // Time complexity - o(m + n) assuming we have to do a comparison for every node
    // Space complexity - o(1) only a fixed size of node to create the head and helper, the rest would just be simply pointing to the existing nodes
    public ListNode<Integer> mergeTwoSortedList(ListNode<Integer> listOne, ListNode<Integer> listTwo) {
        // Initiate a new Node
        ListNode<Integer> mergedList = new ListNode<>(0);
        ListNode<Integer> helper = mergedList;

        while (listOne.val != null && listTwo.val != null) {
            if (listOne.val <= listTwo.val) {
                helper.next = listOne;
                listOne = listOne.next;
            } else {
                helper.next = listTwo;
                listTwo = listTwo.next;
            }
            helper = helper.next;
        }
        // At the end append the remainder of the bigger list
        if (listOne.val != null) {
            helper.next = listOne;
        } else if (listTwo.val != null) {
            helper.next = listTwo;
        }
        return mergedList.next;
    }

    // * Reorder Lists
    public void reorderList(ListNode<Integer> head) {
        ListNode<Integer> slowPointer = head;
        ListNode<Integer> fastPointer = head.next;
        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        ListNode<Integer> secondHalf = slowPointer.next;
        slowPointer.next = null; // Breaks the LinkedList into two halves
        ListNode<Integer> prev = null;
        while (secondHalf != null) { // Reverse the second half of the list
            ListNode<Integer> temp = secondHalf.next;
            secondHalf.next = prev;
            prev = secondHalf;
            secondHalf = temp;
        }

        // Proceed to re-assign the pointers to match the pattern
        ListNode<Integer> firstHalf = head;
        secondHalf = prev;
        while (secondHalf != null) { // Second half should always be the shorter one
            ListNode<Integer> tempOne = firstHalf.next;
            ListNode<Integer> tempTwo = secondHalf.next;
            firstHalf.next = secondHalf;
            secondHalf.next = tempOne;
            // Shift the pointers down
            firstHalf = tempOne;
            secondHalf = tempTwo;
        }

    }

    // * 19. Remove Nth Node From End of List
    // Time complexity - o(n) - We have to traverse the list once
    // Space complexity - o(1) - We only need to maintain two pointers
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Initiate two pointers which will keep track of the difference between last and nth
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode left = dummy;
        ListNode right = dummy;
        while (n > 0) {
            right = right.next;
            n--;
        }

        // Move the two nodes until right hits null (Means it is at the end)
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }

        // At this point, right is at the end, so just need point the left node to the corresponding next node
        left.next = left.next.next;

        return dummy.next;
    }

    // * 141 Linked List Cycle
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
