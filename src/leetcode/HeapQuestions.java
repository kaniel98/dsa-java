package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapQuestions {
    public static void main(String[] args) {
    }


    // * 703. Kth Largest Element in a Stream
    // * Time complexity: o (n log n) - Heap insertion is log n, we repeat it n times
    // * Space complexity: o(n)
    class KthLargest {
        public PriorityQueue<Integer> heap = new PriorityQueue<>();
        public int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            for (int num : nums) add(num);
        }

        public int add(int val) {
            if (heap.size() < k) {
                heap.offer(val);
            } else if (heap.peek() < val) {
                // Remove top element and add in the new one
                heap.poll();
                heap.offer(val);
            }
            return heap.peek();
        }
    }

    // * 973. K Closest points to Origin
    // * Time complexity: o (n log n) - Heap insertion is log n, we repeat it n times
    // * Space complexity: o(n)
    public int[][] kClosest(int[][] points, int k) {
        Comparator<int[]> distanceComparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return distanceToOrigin(o1) - distanceToOrigin(o2);
            }

            private int distanceToOrigin(int[] p) {
                return (int) (Math.pow(p[0], 2) + Math.pow(p[1], 2));
            }
        };

        PriorityQueue<int[]> heap = new PriorityQueue<>(points.length, distanceComparator);
        heap.addAll(Arrays.asList(points));

        int[][] result = new int[k][];
        for (int i = 0; i < k; i++) {
            result[i] = heap.poll();
        }

        return result;
    }

    public class ListNode {
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

    // * 23. Merge k Sorted Lists
    // * Time complexity - o ( n log k) - Each insertion into the heap takes log k time, this process is
    // repeated n times
    // * Space complexity - o (k)
    public ListNode mergeKLists(ListNode[] lists) {
        // * Solution: put into a min heap list, pop everything out and construct the new linked list
        // If we want it to be descending, reverse a and b
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);


        if (lists == null || lists.length == 0) {
            return null;
        }

        // Put the current ListNodes into the heap
        for (ListNode node : lists) {
            if (node != null) {
                heap.offer(node);
            }
        }

        // Pop and add the next node back into the list (If applicable)
        ListNode result = new ListNode(0);
        ListNode temp = result;
        while (!heap.isEmpty()) {
            ListNode curr = heap.poll();
            temp.next = curr;
            if (curr.next != null) {
                heap.offer(curr.next);
            }
            temp = temp.next;
        }

        return result.next;
    }

    // * 215. Kth Largest Element in an Array
    // * Time complexity - o (n log n) (Insert is log n, repeat n times)
    // * To remove each node is o ( k log n)
    // * Space complexity - o (n)
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((a, b) -> b - a);

        for (int num : nums) {
            heap.add(num);
        }

        for (int i = 0; i < k - 1; i++) {
            heap.poll();
        }
        return heap.poll();
    }

}
