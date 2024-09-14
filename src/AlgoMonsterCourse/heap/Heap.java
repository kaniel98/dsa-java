package AlgoMonsterCourse.heap;

import java.util.*;

public class Heap {
    public static void main(String[] args) {

    }

    // * Basic way of using a heap in java
    public static List<Integer> returnTopThree(List<Integer> arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.addAll(arr);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(heap.poll());
        }
        return result;
    }

    // * 973. K Closest Points to Origin
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

    // 621. Task Scheduler
    // Time complexity - o(n log n) - log n to insert and pop and repeated n times
    // Space complexity - o(n)
    public int leastInterval(char[] tasks, int n) {
        // Use a max heap to keep track of the count of each character
        // Everytime we remove the most frequent - The task is executed
        // Add the character into a queue (To keep track of the idle time before we can add it back)
        // Keep a time counter to keep track of when the pop the queue
        if (n == 0) return tasks.length;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a); // Max heap
        ArrayDeque<List<Integer>> queue = new ArrayDeque<>(); // Queue

        // Get the count of each character
        int[] arr = new int[26]; // 26 Characters
        for (char c : tasks) {
            arr[c - 'A']++;
        }
        

        // Put the count into the heap
        for (int num : arr) {
            if (num > 0) maxHeap.add(num);
        }

        int timer = 0;
        while (!maxHeap.isEmpty() || !queue.isEmpty()) {
            if (!maxHeap.isEmpty()) {
                int currentTask = maxHeap.poll();
                if (currentTask - 1 > 0) { // If there is more thn 0 of such task left, add it to queue
                    queue.offer(List.of(currentTask - 1, timer + n));
                }
            }

            if (!queue.isEmpty() && queue.peek().get(1) == timer) { // If the first element in the queue is ready to be added back, pop it and add it back to heap
                maxHeap.add(queue.poll().get(0));
            }
            timer++;
        }
        return timer;
    }
}
