package leetcode;

import java.util.*;

public class IntervalQuestions {
    public static void main(String[] args) {
    }

    // * 56. Merge Intervals - Heap solution, but not ideal
    // * Time complexity - o (n log n) (To insert and poll)
    // * Space complexity - o (n)
    public int[][] merge(int[][] intervals) {
        // Sort based on the start
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int[] interval : intervals) {
            heap.add(interval);
        }

        List<int[]> result = new ArrayList<>();

        while (!heap.isEmpty()) {
            int[] curr = heap.poll();

            while (!heap.isEmpty()) {
                int[] next = heap.poll();
                if (curr[1] >= next[0]) {
                    curr[1] = Math.max(curr[1], next[1]);
                } else {
                    heap.add(next);
                    break;
                }
            }
            result.add(curr);
        }
        return result.toArray(new int[result.size()][]);
    }

    public int[][] mergeV2(int[][] intervals) {
        ArrayList<int[]> result = new ArrayList<>();

        // Sort the intervals based on the first digit
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // From here merge the result into the result array
        for (int i = 0; i < intervals.length; i++) {
            int[] currInterval = intervals[i];
            if (!result.isEmpty() && currInterval[0] <= result.getLast()[1]) {
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], currInterval[1]);
            } else {
                result.add(currInterval);
            }
        }

        // Return the result
        int[][] res = new int[result.size()][];
        result.toArray(res);
        return res;
    }

    // * 435. Non-overlapping Intervals
    // * Time complexity: o (n log n) - Sorting of array
    // * Space complexity: o(1) - No extra space used
    public int eraseOverlapIntervals(int[][] intervals) {
        int removedIntervals = 0;
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int[] prev = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] currInterval = intervals[i];
            // Case 1: Two can have the same point - Not considered overlapping
            if (prev[1] > currInterval[0]) {
                removedIntervals++;
                // Case 2: We will choose to remove the interval with the earlier endpoint = Lesser chance of overlapping
                if (currInterval[1] < prev[1]) {
                    prev = currInterval;
                }
            } else {
                prev = currInterval;
            }
        }

        return removedIntervals;
    }

    // * 57. Insert Interval
    // * Time complexity: o(n) - Iterate through the array once
    // * Space complexity: o(n) - Size of the new array
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> store = new ArrayList<>();

        for (int[] interval : intervals) {
            // Case 1: The current interval does not overlap with the new interval at all, interval is smaller
            if (newInterval == null || interval[1] < newInterval[0]) {
                store.add(interval);

                // Case 2: If the new interval is smaller than the curr interval, add both and set interval to null to prevent it from being added again
            } else if (newInterval[1] < interval[0]) {
                store.add(newInterval);
                store.add(interval);
                newInterval = null;

                // Case 3: The two intervals are overlapping, thus we will merge it
            } else {
                newInterval = new int[]{
                        Math.min(interval[0], newInterval[0]),
                        Math.max(interval[1], newInterval[1]),
                };
            }
        }

        if (newInterval != null) {
            store.add(newInterval);
        }


        int[][] result = new int[store.size()][];
        return store.toArray(result);
    }

    // * Meeting rooms I
    // * Time complexity: o(n log n) - Sorting
    // * Space complexity: o(1) - No extra space needed
    public boolean canAttendMeetings(List<Interval> intervals) {
        // 1. Sort the intervals
        // 2. Iterate through
        // - As long as the second index is less then the first index, means it is ok
        intervals.sort((a, b) -> a.start - b.start);
        for (int i = 0; i < intervals.size() - 1; i++) {
            Interval currInterval = intervals.get(i);
            Interval nextInterval = intervals.get(i + 1);
            if (currInterval.end > nextInterval.start) {
                return false;
            }
        }
        return true;
    }

    private class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // * Meeting room II
    // * Time complexity - o (n log n)
    // * Space complexity - o (n)
    public int minMeetingRooms(List<Interval> intervals) {
        // Draw this out, we will see we basically need to find the number of concurrent meetings
        // Form two arrays for start and end time
        // From there, check the number of overlapping

        // 1. Put the intervals into the start and end times
        List<Integer> startTimes = new ArrayList<>();
        List<Integer> endTimes = new ArrayList<>();

        // 2. Sort the intervals
        for (Interval interval : intervals) {
            startTimes.add(interval.start);
            endTimes.add(interval.end);
        }
        Collections.sort(startTimes);
        Collections.sort(endTimes);

        // 3. Maintain a counter for the number of concurrent time at any point in time
        int count = 0;
        int max = count;

        // e.g.,
        // [0, 0, 10, 10, 20, 20, 30, 30, 40, 40, 50, 60, 70, 80, 90]
        // [10, 20, 30, 40, 50, 60, 60, 70, 70, 80, 80, 90, 90, 100, 100]
        int endPointer = 0;
        int startPointer = 0;
        while (startPointer < startTimes.size()) {
            // If the current start is lesser than the first end, it means the meeting still ongoing
            if (startTimes.get(startPointer) < endTimes.get(endPointer)) {
                count++;
                startPointer++;
            }

            // If the current start is later than the end, move the end up
            // Decrement the count by 1 (Meeting ended)
            else {
                count--;
                endPointer++;
            }
            max = Math.max(count, max);
        }

        return max;
    }
}
