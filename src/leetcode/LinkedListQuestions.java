package leetcode;

public class LinkedListQuestions {
    // Definition of singly-linked list
    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // * 206. Reverse Linked List
    // * Time Complexity - O(n)
    // * Space Complexity O(1)
    public ListNode reverseList(ListNode head) {
        ListNode temp = head;
        ListNode after = null;
        ListNode before = null;

        while (temp != null) {
            after = temp.next;
            temp.next = before;
            before = temp;
            temp = after;
        }
        return before;
    }

    // * 21. Merged Linked List
    // * Time complexity - o(n + m)
    // * Space complexity - o(1) (No extra memory was used)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode mergedList = new ListNode(0);
        ListNode helper = mergedList;
        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                helper.next = list1;
                list1 = list1.next;
            } else {
                helper.next = list2;
                list2 = list2.next;
            }
            helper = helper.next; // Move it to the next
        }
        // Remainder of either list1 or list2;
        if (list1 != null) {
            helper.next = list1;
        } else if (list2 != null) {
            helper.next = list2;
        }
        return mergedList.next;
    }

    // * 143. Reorder List
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public void reorderList(ListNode head) {
        // 1. Use Fast and Slow pointers to find the middle of the linked list
        ListNode slowPointer = head;
        ListNode fastPointer = head.next;
        // Need to check for fast pointer
        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        // 2. Reverse the second half
        ListNode secondHalf = slowPointer.next;
        slowPointer.next = null; // Breaks it off from the first half
        ListNode prev = null;
        while (secondHalf != null) {
            ListNode temp = secondHalf.next; // After
            secondHalf.next = prev;
            prev = secondHalf;
            secondHalf = temp;
        }

        // 3. Approach this like a merged two list question
        ListNode firstHalf = head;
        secondHalf = prev;
        while (secondHalf != null) {// Second half should be the shorter one
            ListNode tempOne = firstHalf.next;
            ListNode tempTwo = secondHalf.next;
            // Re arrange
            firstHalf.next = secondHalf; // Second half's start will be firstHalf's next number
            secondHalf.next = tempOne; // first half's start will be second half's next
            // Move both pointers to next
            firstHalf = tempOne;
            secondHalf = tempTwo;
        }
    }
}
