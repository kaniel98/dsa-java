package leetcode;

import leetcode.sub.ListNode;

import java.util.*;

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

    // * 1845. Seat reservation manager
    // * Time complexity: o (n log n) (Insert is log n, repeat n times)
    // * Space complexity: o(n)
    class SeatManager {
        PriorityQueue<Integer> queue;

        public SeatManager(int n) {
            queue = new PriorityQueue<>(n);
            for (int seat = 1; seat <= n; seat++) {
                queue.add(seat);
            }
        }

        public int reserve() {
            return queue.poll();
        }

        public void unreserve(int seatNumber) {
            queue.offer(seatNumber);
        }
    }

    // * 692. Top K Frequent Words
    // * Time complexity: o(n log n) insert log n, repeated n times
    // * Space complexity: o(n)
    public List<String> topKFrequent(String[] words, int k) {
        // Sort based on the occurence;
        PriorityQueue<WordValue> heap = new PriorityQueue<>((a, b) -> {
            if (b.wordCount != a.wordCount) {
                return b.wordCount - a.wordCount;
            }
            return a.word.compareTo(b.word);
        });

        HashMap<String, Integer> store = new HashMap<>();
        for (String word : words) {
            store.put(word, store.getOrDefault(word, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : store.entrySet()) {
            heap.offer(new WordValue(entry.getKey(), entry.getValue()));
        }

        ArrayList<String> result = new ArrayList<>();
        while (k > 0 && !heap.isEmpty()) {
            WordValue word = heap.poll();
            result.add(word.word);
            k--;
        }

        return result;
    }

    public static class WordValue {
        String word;
        int wordCount;

        public WordValue(String word, int wordCount) {
            this.word = word;
            this.wordCount = wordCount;
        }
    }

    // * 148. Sort List
    // * Time complexity - o (n log n) (To sort and insert)
    // * Space complexity - o (n)
    public ListNode sortList(ListNode head) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        ListNode temp = new ListNode(0);
        ListNode dummy = temp;

        while (head != null) {
            heap.offer(head.val);
            head = head.next;
        }
        while (!heap.isEmpty()) {
            ListNode curr = new ListNode(heap.poll());
            dummy.next = curr;
            dummy = dummy.next;
        }

        return temp.next;
    }

    // * 1942. The Number of the Smallest Unoccupied Chair
    // * Time complexity: o (n log n) - Insertion of times
    // * Space complexity: o(n)
    public int smallestChair(int[][] times, int targetFriend) {
        /*
            1. Two heaps can be used to keep track of arrival time and available chairs (Min heaps)
            2. Sort the array based on the arrival time
            3. For every arrival - Check who left, put those chairs back into available chair seats, assign the next
            available seat to the current person
            4. At this point of time check if the start time is the same as the current arrival (It is the target)
            Note: This works because each arrival time is distinct
         */
        PriorityQueue<int[]> usedChairs = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> availChairs = new PriorityQueue<>();

        // Target friend's arrival time
        int targetFriendArrivalTime = times[targetFriend][0]; // Arrival time is distinct
        int nextCharAvailable = 0;

        // Sort the array based on the time which they arrive
        Arrays.sort(times, (a, b) -> a[0] - b[0]);

        // Iterate through each time, to check what is the taken chairs and avail char at the given start and end
        for (int[] time : times) {
            int start = time[0];
            int end = time[1];

            // If used chairs is not empty && the leave time of the used chairs is <= the start time, add it to avail chair
            while (!usedChairs.isEmpty() && usedChairs.peek()[0] <= start) {
                availChairs.offer(usedChairs.poll()[1]);
            }

            // Assign the next available chair to this current friend
            int sat = 0;
            if (!availChairs.isEmpty()) {
                sat = availChairs.poll();
            } else {
                sat = nextCharAvailable;
                nextCharAvailable++;
            }

            // Add it to the current list
            usedChairs.offer(new int[]{end, sat});

            // If the target time matches, return it
            if (start == targetFriendArrivalTime) {
                return sat;
            }
        }
        return -1;
    }

    // * 2530. Maximal Score After Applying K Operations
    // * Time complexity: o (n log n)
    // * Space complexity: o(n)
    public long maxKElements(int[] nums, int k) {
        long result = 0;

        // 1. Maintain a max heap
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int num : nums) {
            pq.offer(num);
        }

        // 2. While k isn't empty, pop the first element, add it to sum
        while (k > 0 && !pq.isEmpty()) {
            // 3. Apply operation to the element and put it back into the heap
            int curr = pq.poll();
            result += (long) curr;
            pq.offer((int) Math.ceil((double) curr / 3));
            k--;
        }

        // 4. Decrease k by 1 and repeat process until k = 0
        return result;
    }

    // * 1471. The k Strongest Values in an Array
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n) - Storing the values
    public int[] getStrongest(int[] arr, int k) {
        // Sort the array and get the median
        // Put the numbers into a priority queue
        // Pop until the kth element
        Arrays.sort(arr);
        // Get the median number
        int median = arr[(arr.length - 1) / 2];

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (b[1] == a[1]) {
                return b[0] - a[0];
            }
            return b[1] - a[1];
        });

        for (int num : arr) {
            pq.offer(new int[]{num, Math.abs(num - median)});
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = pq.poll()[0];
        }

        return result;
    }

    // * 1405. Longest Happy String
    // * Time complexity: o(n) - Total number of characters
    // * Space complexity: o(1) - The heap is consistent size
    public String longestDiverseString(int a, int b, int c) {
        // 1. Question is asking for the longest string where the characters do not repeat itself
        // 2. Start by adding the most common
        // 3. Followed by adding the the most common

        PriorityQueue<CharCount> pq = new PriorityQueue<>((x, y) -> y.count - x.count);
        if (a > 0) {
            pq.offer(new CharCount(a, 'a'));
        }
        if (b > 0) {
            pq.offer(new CharCount(b, 'b'));
        }
        if (c > 0) {
            pq.offer(new CharCount(c, 'c'));
        }

        StringBuilder sb = new StringBuilder();

        while (!pq.isEmpty()) {
            CharCount node = pq.poll();

            // Check if the last two characters match the current count;
            if (sb.length() >= 2 && sb.charAt(sb.length() - 1) == node.chr && sb.charAt(sb.length() - 2) == node.chr) {
                if (pq.isEmpty()) {
                    return sb.toString();
                }
                // Else add the next common one to the String builder and add it back
                CharCount nodeTwo = pq.poll();
                sb.append(nodeTwo.chr);
                if (nodeTwo.count - 1 > 0) {
                    nodeTwo.count -= 1;
                    pq.offer(nodeTwo);
                }
            } else {
                sb.append(node.chr);
                node.count -= 1;
            }

            if (node.count > 0) {
                pq.offer(node);
            }
        }

        return sb.toString();
    }

    static class CharCount {
        int count;
        char chr;

        public CharCount(int count, char chr) {
            this.count = count;
            this.chr = chr;
        }
    }

    // * 264. Ugly Number II
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n)
    int[] primeNumbers = new int[]{2, 3, 5};

    public int nthUglyNumber(int n) {

        PriorityQueue<Long> pq = new PriorityQueue<>();
        Set<Long> insertedNumbers = new HashSet<>();

        pq.offer(1L);
        insertedNumbers.add(1L);

        for (int i = 0; i < n - 1; i++) {
            long num = pq.poll();
            System.out.println(num);
            for (int prime : primeNumbers) {
                if (!insertedNumbers.contains(num * prime)) {
                    insertedNumbers.add(num * prime);
                    pq.offer(num * prime);
                }
            }
        }
        System.out.println(pq);
        long result = pq.poll();
        return (int) result;
    }

    // * 1962. Remove Stones to Minimize the Total
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n)
    public int minStoneSum(int[] piles, int k) {
        // Max heap
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int pile : piles) {
            pq.offer(pile);
        }

        for (int i = 0; i < k; i++) {
            int pile = pq.poll();
            pile -= (int) Math.floor(pile / 2);
            if (pile == 0) {
                continue;
            }
            pq.offer(pile);
        }

        int sum = 0;
        while (!pq.isEmpty()) {
            sum += pq.poll();
        }
        return sum;
    }

    // * 3075. Maximum happiness of selected children
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n) - Storing the values
    // * Note that question can also be solved just by sorting the array
    public long maximumHappinessSum(int[] happiness, int k) {
        // 1. Put the happiness into priority queue
        // 2. Have a variable to keep track of the number of turns
        // 3. Everytime pop, just reduce it by the number of turns
        // 4. Return the sum
        int turnsTaken = 0;
        long totalHappiness = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int h : happiness) {
            pq.offer(h);
        }

        while (k > 0) {
            long h = Math.max((long) pq.poll() - turnsTaken, 0);
            totalHappiness += h;
            k--;
            turnsTaken++;
        }

        return totalHappiness;
    }

    // * 862. Shortest Subarray with Sum at Least K - Heap
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n)
    // * Better solution is to use a Monotonic Deque
    public int shortestSubarray(int[] nums, int k) {
        // Prefix & Heap Approach
        // Main goal: Remove the minimum prefix that would result in the subarray = k && the prefix with the largest index
        long currentSum = 0L;
        int res = Integer.MAX_VALUE;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> {
            // Sort by prefix first
            if (a[0] == b[0]) {
                return Long.compare(b[1], a[1]); // We want the largest index at the start;
            }
            return Long.compare(a[0], b[0]);
        });

        // Iterate through the array
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];

            // Consider a situation where the sub array is more than or equals to k
            if (currentSum >= k) {
                res = Math.min(res, right + 1);
            }

            // Proceed to pop the heap while it can still result into a subarray with the target sum
            while (!pq.isEmpty() && currentSum - pq.peek()[0] >= k) {
                long[] prefix = pq.poll();
                res = (int) Math.min(res, right - prefix[1]);
            }

            // Afterwards we will push the current sum back into heap
            pq.offer(new long[]{currentSum, (long) right});
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // * 862. Shortest Subarray with Sum at Least K - Monotonic Deque
    // * Time complexity: o(n) - We only iterate through the array once
    // * Space complexity: o(n) - We only store the prefix
    public int shortestSubarrayMonotonic(int[] nums, int k) {
        ArrayDeque<long[]> deque = new ArrayDeque<>();
        long currentSum = 0;
        int res = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            if (currentSum >= k) {
                res = Math.min(res, right + 1);
            }

            // Proceed to check the queue from the start
            // The ones which match the conditions will be popped (No point to reuse)
            // Leave only the larger prefix which might meet the condition further down the line
            while (!deque.isEmpty() && currentSum - deque.peek()[0] >= k) {
                long[] prefix = deque.poll();
                res = (int) Math.min(res, right - prefix[1]);
            }

            // Goal is to establish a monotonic INCREASING deque, we dont need decreasing, prefix, we only need increasing
            // Prioritise a smaller prefix with larger index over a larger prefix with smaller idnex
            while (!deque.isEmpty() && deque.peekLast()[0] >= currentSum) {
                deque.removeLast();
            }
            deque.offer(new long[]{currentSum, right});
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // * 2558. Take Gifts From the Richest Pile
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n)
    public long pickGifts(int[] gifts, int k) {
        // Max heap
        PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> Long.compare(b, a));
        for (long gift : gifts) {
            pq.offer(gift);
        }

        while (k > 0) {
            long gift = pq.poll();
            pq.offer((long) Math.sqrt(gift));
            k--;
        }

        long result = 0;
        while (!pq.isEmpty()) {
            result += pq.poll();
        }

        return result;
    }

    // * 2593. Find Score of an Array After Marking All Elements
    // * Time complexity: o(n log n) - Insertion is log n, repeated n times
    // * Space complexity: o(n)
    public long findScore(int[] nums) {
        long result = 0;
        // 1st is the value, 2nd is the index
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });

        for (int i = 0; i < nums.length; i++) {
            pq.offer(new int[]{nums[i], i});
        }

        while (!pq.isEmpty()) {
            int[] smallest = pq.poll();
            int idx = smallest[1];
            if (nums[idx] == -1) {
                continue; // Means this was already marked, continue
            }

            result += Long.valueOf(smallest[0]);
            nums[idx] = -1;
            if (idx > 0) nums[idx - 1] = -1;
            if (idx < nums.length - 1) nums[idx + 1] = -1;
        }

        return result;
    }
}
