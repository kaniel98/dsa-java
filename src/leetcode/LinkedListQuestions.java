package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LinkedListQuestions {
    // Definition of singly-linked list
    class ListNode {
        Integer val;
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

    /*
     * Questions done:
     * 206. Reverse Linked List
     * 21. Merged Linked List
     * 143. Reorder List
     * 19. Remove Nth Node From End of List
     * 2. Add two numbers
     * 141. Linked List Cycle
     * 146. LRU Cache
     * 2487. Remove Nodes from Linked List
     * 2130. Maximum Twin Sum of a Linked List
     * 1721. Swapping Nodes in a Linked List
     */

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

    // * 19. Remove Nth Node From End of List
    // * Time complexity - o(n)
    // * Space complexity - o(1);
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode left = dummy;
        ListNode right = dummy;
        while (n > 0) {
            right = right.next; // Moves right to the distance it is expected to be from left
            n--;
        }

        // Proceed to move both nodes until right hits null
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }

        // At this point, right is at the end while left's next is the node to be removed
        left.next = left.next.next; // Singly linked list so don't need to adjust
        return dummy.next;
    }

    // * 2. Add two numbers
    // * Time complexity - o(n + m) need to iterate through both l1 and l2
    // * Space complexity - o (n + m)
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode helper = dummyNode;
        int carryOver = 0;

        while (l1 != null || l2 != null || carryOver > 0) {
            int listOneVal = l1 == null ? 0 : l1.val;
            int listTwoVal = l2 == null ? 0 : l2.val;
            // Proceed to add for the carry over
            int val = listTwoVal + listOneVal + carryOver;

            // Accomodate for the carry over
            carryOver = val / 10; // Get the carry over
            val = val % 10; // Keep only the last digit
            helper.next = new ListNode(val);

            // Update the pointers
            helper = helper.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return dummyNode.next;
    }

    // * 141. Linked List Cycle
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public boolean hasCycle(ListNode head) {
        // Slow and Fast pointer
        // While Fast pointer is not null, keep moving
        // If fast == slow, means there is a cycle
        // If null = no cycle
        ListNode fastPointer = head;
        ListNode slowPointer = head;
        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
            if (slowPointer == fastPointer) {
                return true;
            }
        }
        return false;
    }

    // * 146. LRU Cache
    class LRUCache {
        private Map<Integer, Node> store;
        private int capacity;
        private Node head; // Keeps track of least recently used
        private Node tail; // Keeps track of the most recently used

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.store = new HashMap<>();

            // Initiate the head and tail (Addresses null checks)
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public int get(int key) {
            if (!store.containsKey(key)) {
                return -1;
            }
            // Updating the pointers
            remove(store.get(key)); // Remove from current position
            insert(store.get(key)); // Move it to the tail
            return store.get(key).value; // Return the value;
        }

        public void put(int key, int value) {
            if (store.containsKey(key)) {
                remove(store.get(key));
            }
            store.put(key, new Node(key, value));
            insert(store.get(key)); // Move it to the most recently used

            // Check if the cache is more than capacity
            // If yes, proceed to evict the least recently used.
            if (store.size() > capacity) {
                Node leastUsed = this.head.next;
                // Remove from the node
                remove(leastUsed);
                store.remove(leastUsed.key);
            }
        }

        private void remove(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
        }

        public void insert(Node node) {
            // Inserting it at the tail - Most recently used;
            Node prev = this.tail.prev;
            Node next = this.tail;

            // Linking the before and after
            prev.next = node;
            next.prev = node;

            // Linking the node
            node.next = next;
            node.prev = prev;
        }

        // Double linked list question - you are trying to keep a specific order
        class Node {
            Node prev;
            Node next;
            int value;
            int key;

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }

    // * 2487. Remove Nodes from Linked List
    // * Notes: Related to the concept of Monotonic Stack
    // * Time Complexity: o(n)
    // * Space complexity: o(n)
    public ListNode removeNodes(ListNode head) {
        // 1. Approach this as a stack question
        // 2. Put the node into the stack,
        // 3. If the next node is bigger, pop the item in the stack
        // 4. Repeat until the next item is bigger or if the stack is empty
        ArrayList<Integer> stack = new ArrayList<>();
        while (head != null) {
            while (!stack.isEmpty() && (stack.getLast() < head.val)) {
                stack.removeLast();
            }
            stack.add(head.val);
            head = head.next;
        }
        // 5. Reconstruct the linked list
        ListNode temp = new ListNode();
        ListNode dummy = temp;
        for (int i : stack) {
            dummy.next = new ListNode(i);
            dummy = dummy.next;
        }
        return temp.next;
    }

    // * 2487 Remove nodes from Linked List
    // * Utilises pointer approach - Reverse linked list one, remove the smaller nodes and reverse back again
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public ListNode removeNodesVersionTwo(ListNode head) {
        head = reverseList(head);
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.next.val < temp.val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;

            }
        }
        return reverseList(head);
    }

    // * 2130. Maximum Twin Sum of a Linked List
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public int pairSum(ListNode head) {
        int maxValue = Integer.MIN_VALUE;
        ListNode slow = head;
        ListNode fast = head;

        // 1. Navigate to the middle of the list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse that half of the list
        // By the time it has finished, slow will be at the middle (even)
        ListNode rightEnd = reverseLinkedList(slow);
        ListNode leftEnd = head;

        // 3. Iterate through both list to find the max sum and return it
        while (rightEnd != null) {
            maxValue = Math.max(leftEnd.val + rightEnd.val, maxValue);
            leftEnd = leftEnd.next;
            rightEnd = rightEnd.next;
        }
        return maxValue;
    }

    private ListNode reverseLinkedList(ListNode head) {
        if (head == null) return head;

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

    // * 1721. Swapping Nodes in a Linked List
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        Integer n = k - 1;

        // Move the second node to be k distance away from the first node
        while (n > 0) {
            second = second.next;
            n--;
        }

        Integer count = 0;
        // The current second node is the node before Kth
        ListNode nodeBeforeFirstK = second;
        // At this point in time, move both nodes until second reaches the end.
        while (second.next != null) {
            count++;
            second = second.next;
            first = first.next;
        }

        // Once second hits the next, first's next is the target node
        // Swap first and nodeBeforeFirstK

        // Switching the before
        int temp = nodeBeforeFirstK.next.val;
        nodeBeforeFirstK.next.val = first.val;
        first.val = temp;

        return dummy.next;
    }

}
