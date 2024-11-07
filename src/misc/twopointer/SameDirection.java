package misc.twopointer;

import java.util.List;
import java.util.Objects;

// * Refer to two pointer questions where they move in the same direction
// * e.g., remove duplicate
public class SameDirection {
    public static void main(String[] args) {

    }

    // * Remove duplicates
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static int removeDuplicates(List<Integer> arr) {
        // WRITE YOUR BRILLIANT CODE HERE
        // Assuming that the arr is sorted
        // 1. Initiate left and right pointer
        // 2. Right pointer will move until there isnt any duplicate
        // 3. Left + 1 will be equal to right (Basically removing the duplicates in between
        // 4. Repeat process until left reaches the end

        int left = 0;
        for (int right = 0; right < arr.size(); right++) {
            if (right == 0 || Objects.equals(arr.get(right), arr.get(left))) {
                continue;
            }

            // * Else, update left pointer
            left++;
            arr.set(left, arr.get(right));
        }
        return left + 1; // Because we started at 0
    }

    // * Middle of a linked list
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static Integer middleOfLinkedList(Node<Integer> head) {
        // WRITE YOUR BRILLIANT CODE HERE
        // * Initiate two pointers - Fast and Slow
        // Fast will move twice as fast, once it reaches the end, return slow
        Node<Integer> slow = head;
        Node<Integer> fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow.val;
    }

    public static class Node<T> {
        public T val;
        public Node<T> next;

        public Node(T val) {
            this(val, null);
        }

        public Node(T val, Node<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    // * Move zeros
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static void moveZeros(List<Integer> nums) {
        // WRITE YOUR BRILLIANT CODE HERE
        // 1. Initiate fast and slow pointer
        // 2. Slow pointer will always be pointing to the next zero (if present, else return)
        // 3. Move fast after the slow pointer, everytime fast meets a non-zero, swap it
        int slow = 0;
        for (int fast = 0; fast < nums.size(); fast++) {
            if (nums.get(fast) != 0) {
                int slowNum = nums.get(slow);
                nums.set(slow, nums.get(fast));
                nums.set(fast, slowNum);
                slow++;
            }
        }
    }
}
