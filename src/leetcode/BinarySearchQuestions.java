package leetcode;

import java.util.*;

public class BinarySearchQuestions {
    public static void main(String[] args) {
        int[] input = new int[]{4, 5, 6, 7, 0, 1, 2};
        BinarySearchQuestions test = new BinarySearchQuestions();
        test.successfulPairs(new int[]{5, 1, 3}, new int[]{1, 2, 3, 4, 5}, 7);
    }

    // * 704. Binary Search (Basic)
    // * Time complexity: O(n log n)
    // * Space complexity: o(1) - Two pointer only
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) {
                left++;
            } else {
                right--;
            }
        }
        return -1;
    }

    // * 74. Search a 2D Matrix
    // * Time complexity
    // * Space complexity
    public boolean searchMatrix(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length - 1;
        int[] innerArray = new int[]{};
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[mid][0] <= target && matrix[mid][matrix[mid].length - 1] >= target) {
                innerArray = matrix[mid];
                break;
            }
            if (target > matrix[mid][0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (innerArray.length == 0) {
            return false;
        }

        // Proceed to do normal binary search
        left = 0;
        right = innerArray.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (innerArray[mid] == target) return true;
            if (innerArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    // * 875. Koko eating bananas
    // * Time complexity - O(n log n)
    // * Space complexity - O(1)
    public int minEatingSpeed(int[] piles, int h) {
        int left = 0;
        int right = Arrays.stream(piles).max().getAsInt();
        int result = left;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // Check if the current number of bananas is feasible
            if (isFeasible(piles, h, mid)) {
                result = mid;
                // If it is feasible, we can reduce the amount again
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private boolean isFeasible(int[] piles, int h, int numberOfBananaPerHour) {
        double totalTime = 0;
        for (int pile : piles) {
            totalTime += Math.ceil((double) pile / numberOfBananaPerHour);
        }
        return totalTime <= h;
    }

    // * 153 Find Minimum in rotated sorted array
    // * Time complexity - O (n log n)
    // * Space complexity - O(1)
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int boundary = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            // As long as middle is less than the end of the array, it means the minimum is at the start of the array
            if (nums[middle] <= nums[nums.length - 1]) {
                boundary = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return nums[boundary];
    }

    // * 33. Search in rotated sorted array
    // * Time complexity - o(n log n)
    // * Space complexity - o(1)
    public int searchInRotatedSortedArray(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = (right + left) / 2;
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[left] <= nums[middle]) {
                // This would mean that we are in the left sorted array, do binary search logic on the left sorted
                if (target > nums[middle] || target < nums[left]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            } else {
                // This would mean we are in the right sorted array, do binary search logic on the right\
                if (target < nums[middle] || target > nums[right]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
        }
        return -1;
    }

    // * 981. Design a Time Based Key-Value store
    // * Time complexity - O(n log n)
    class TimeMap {
        private Map<String, List<List<Object>>> store;

        public TimeMap() {
            store = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            if (!store.containsKey(key)) {
                store.put(key, new ArrayList<>());
            }
            store.get(key).add(List.of(timestamp, value));
        }

        public String get(String key, int timestamp) {
            if (!store.containsKey(key)) {
                return "";
            }
            return search(store.get(key), timestamp);
        }

        public String search(List<List<Object>> list, int timestamp) {
            int left = 0;
            int right = list.size() - 1;
            while (left < right) {
                int middle = left + (right - left + 1) / 2;
                // Goal is to get as close to the request timestamp as possible WITHOUT going
                // above, thus we will not +1
                if ((int) list.get(middle).get(0) <= timestamp) {
                    left = middle;
                } else {
                    right = middle - 1;
                }
            }
            // Lastly, check if left is present (It should either be the closest to timestamp or no)
            return (int) list.get(left).get(0) <= timestamp ? (String) list.get(left).get(1) : "";
        }
    }

    // * 34. Find first and last position of element in sorted array
    // * Time complexity - o(log n)
    // * Space complexity - o(1)
    public int[] searchRange(int[] nums, int target) {
        // Do binary search to find the target;
        // Use another simple function to + and - until the edges are found
        int left = 0;
        int right = nums.length - 1;
        int targetPoint = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                targetPoint = middle;
                break;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return searchEdges(nums, target, targetPoint);
    }

    public int[] searchEdges(int[] nums, int target, int mid) {
        if (mid == -1) {
            return new int[]{-1, -1};
        }

        int left = mid;
        int right = mid;
        int[] result = {left, right};

        while (left >= 0 && nums[left] == target) {
            result[0] = left;
            left--;
        }

        while (right <= nums.length - 1 && nums[right] == target) {
            result[1] = right;
            right++;
        }

        return result;
    }

    // * 2300. Successful pairs of spells and potions
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        // 1. Sort the potions
        Arrays.sort(potions);
        int[] result = new int[spells.length];

        int firstSuccessfulPair = potions.length;

        for (int i = 0; i < spells.length; i++) {
            int left = 0;
            int right = potions.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if ((long) spells[i] * potions[mid] >= success) {
                    firstSuccessfulPair = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            result[i] = potions.length - firstSuccessfulPair;
            firstSuccessfulPair = potions.length;
        }

        return result;
    }

    // * 1898. Maximum Number of Removable Characters
    // * Time complexity: o (k log n)
    // * Space complexity: o (k)
    public int maximumRemovals(String s, String p, int[] removable) {
        // Binary search approach to find the last removeable character that still has a subsequence
        // 1. Helper method to check for subsequence
        // 2. Do binaray search on removeable
        int left = 0;
        int right = removable.length - 1;
        int maxChar = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            Set<Integer> removedCharacters = new HashSet<>();
            for (Integer i : Arrays.copyOfRange(removable, 0, mid + 1)) {
                removedCharacters.add(i);
            }

            if (isSubsequence(s, p, removedCharacters)) {
                left = mid + 1;
                maxChar = Math.max(mid + 1, maxChar);
            } else {
                right = mid - 1;
            }

        }
        return maxChar;
    }

    public boolean isSubsequence(String source, String target, Set<Integer> removedCharacters) {
        int sourcePointer = 0;
        int targetPointer = 0;

        while (sourcePointer < source.length() && targetPointer < target.length()) {
            if (removedCharacters.contains(sourcePointer) || source.charAt(sourcePointer) != target.charAt(targetPointer)) {
                sourcePointer++;
                continue;
            }
            sourcePointer++;
            targetPointer++;
        }

        return targetPointer == target.length();
    }

    // * 81. Search in Rotated Sorted Array II
    // * Time complexity - o(n) (If all duplicate numbers) else o(log n)
    // * Space complexity - o(1)
    public boolean searchII(int[] nums, int target) {
        // Search in Rotated Sorted Array I solution does not work here because
        // With multiple duplicate numbers, it prevents us from identifying if the pointer is in first or second half

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            }


            if (nums[left] < nums[mid]) { // Means that this is the first half of the sorted array - Same as original
                if (target > nums[mid] || target < nums[left]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else if (nums[left] > nums[mid]) { // Means second half of the sorted array - Same as original
                if (target < nums[mid] || target > nums[right]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // Else if duplicate number is detected, we would move it forward until non duplicate is detected
                left++;
            }
        }
        return false;
    }

    // * 1552. Magnetic Force Between Two Balls
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public int maxDistance(int[] position, int m) {
        // Sort the array first
        // From there, keep doing binary search between two points that have the greatest difference
        Arrays.sort(position);

        int left = 1;
        // The maximum distance between the different nodes
        // Goal is to find the least possible distance between the nodes based on the given positions
        // = Move right pointer
        // * If it was asking for max possible distance, we will be returning left instead
        int right = (position[position.length - 1] - position[0]) / (m - 1);


        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (isFeasibleMaxDistance(position, mid, m)) {
                left = mid + 1; // Push it forward
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    public boolean isFeasibleMaxDistance(int[] position, int mid, int m) {
        int count = 1;
        int lastPosition = position[0];

        // Only need to iterate until count == m
        for (int i = 1; i < position.length && count < m; i++) {
            // If the distance between the last position and current position is more thn mid = Can put the ball
            if (position[i] - lastPosition >= mid) {
                count++;
                lastPosition = position[i];
            }
        }
        return count == m;
    }


    // * 2594. Minimum Time to Repair Cars
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public long repairCars(int[] ranks, int cars) {
        Arrays.sort(ranks);

        // The longest possible amount of time is the lowest rank fixing every possible car
        long right = ranks[ranks.length - 1] * (long) cars * (long) cars;
        System.out.println(right);
        long left = 1;
        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (isFeasibleRepairCars(mid, ranks, cars)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    public boolean isFeasibleRepairCars(long mid, int[] ranks, int cars) {
        // For a given time limit - Mid
        // Total up the maximum number of cars that each rank can fix
        // If that count is more than or equal to cars, return true
        int count = 0;
        for (int rank : ranks) {
            // Quick way of calculating based on the given formula
            count += (long) Math.sqrt(1.0 * mid / rank);
            if (count >= cars) {
                return true;
            }
        }
        return false;
    }

    // * 1870. Minimum Speed to Arrive on Time
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public int minSpeedOnTime(int[] dist, double hour) {
        int left = 1;
        int right = 10000000; // Boundary is given to be 10 ** 7
        int boundary = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            System.out.println(mid);
            if (isSpeedFeasible(mid, dist, hour)) {
                boundary = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return boundary;
    }

    public boolean isSpeedFeasible(int speed, int[] dist, double hour) {
        double time = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            time += Math.ceil((double) dist[i] / speed);
        }
        time += (double) dist[dist.length - 1] / speed;
        return time <= hour;
    }

    // * 658. Find K Closest Elements
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Approach is to use a sliding window & binary search approach
        // Key point is to keep the start and end of the array to be as close to each other as possible
        int left = 0;
        int right = arr.length - k; // Prevents out of bound exception

        while (left < right) {
            int mid = left + (right - left) / 2;
            // Math.abs is not used because we need keep track of the direction to keep x within sliding window
            // If window too far left (the diff is bigger thn right side), move to right
            // Goal is to minimize the difference between the start and end as much as possible
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
                // Else move to the left;
            } else {
                right = mid;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }

    // * 2563. Count the Number of Fair Pairs
    // * Time complexity: o(n log n)
    // * Space complexity: o(1)
    public long countFairPairs(int[] nums, int lower, int upper) {
        long count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int lowerTarget = lower - num; // lowest number to make sure target + num > lower
            int upperTarget = upper - num; // upper number to make sure that target - num < upper

            count += (upperBound(nums, upperTarget, i + 1, nums.length - 1) -
                    lowerBound(nums, lowerTarget, i + 1, nums.length - 1)) + 1;
        }
        return count;
    }

    public long lowerBound(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Finding the number closest to the lower bound - Left
        return left;
    }

    public long upperBound(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Finding the number closest to the upper bound - Right
        return right;
    }

    // * 540. Single Element in a Sorted Array
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public int singleNonDuplicate(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int boundary = 0;
        if (nums.length == 1) {
            return nums[0];
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid % 2 == 0) {
                if (mid < nums.length - 1 && nums[mid + 1] == nums[mid]) {
                    left = mid + 1;
                } else {
                    boundary = mid;
                    right = mid - 1;
                }
            } else {
                if (mid > 0 && nums[mid - 1] == nums[mid]) {
                    left = mid + 1;
                } else {
                    boundary = mid;
                    right = mid - 1;
                }
            }
        }

        return nums[boundary];
    }

    // * 2064. Minimized Maximum of Products Distributed to any store
    // * Time complexity: o(n log n)
    // * Space complexity: o(1)
    public int minimizedMaximum(int n, int[] quantities) {
        // Binary Search question
        // 1. The max we will have in each store will always be the max quantity in quantities
        // (Because stores will always be more than or equal to number of quantity)
        // From there, it would just be a binary search algorithm to see how much each store can fit
        int left = 0;
        int right = -1;
        for (int quantity : quantities) {
            right = Math.max(quantity, right);
        }
        int max = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            // If feasible, proceed to reduce the amount down
            if (minimizedMaximumFeasible(n, mid, quantities)) {
                max = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return max;
    }

    public boolean minimizedMaximumFeasible(int stores, int mid, int[] quantities) {
        for (int quantity : quantities) {
            stores -= Math.ceil((double) quantity / mid);
            if (stores < 0) {
                return false;
            }
        }
        return true;
    }

    // * 162. Find Peak Element
    // * Time complexity: o(log n)
    // * Space complexity: o(1)
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) { // We are not checking for equality because we want to find the peak
            int mid = (right + left) / 2;

            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left; // Return left or right because both will always be the peak - It will converge towards the end
    }

    // * 1283. Find the Smallest Divisor Given a Threshold
    // * Time complexity: o(n log n)
    // * Space complexity: o(1)
    public int smallestDivisor(int[] nums, int threshold) {
        // Binary search basically

        Arrays.sort(nums);
        int left = 1;
        int right = nums[nums.length - 1];
        int boundary = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (smallestDivisorFeasible(nums, mid, threshold)) {
                boundary = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return boundary;
    }

    public boolean smallestDivisorFeasible(int[] nums, int divisor, int threshold) {
        int sum = 0;
        for (int num : nums) {
            sum += Math.ceil((double) num / divisor);
            if (sum > threshold) {
                return false;
            }
        }
        return true;
    }

    // * 1760. Minimum Limit of Balls in a Bag
    // * Time complexity: o(n log n)
    // * Space complexity: o(1)
    public int minimumSize(int[] nums, int maxOperations) {
        // Similar to binary search question - Target is how many operations can we divide to be within the number of operations
        // For a given "threshhold", how much does each number need to reach it
        // Is the total threshhold within max operations, if yes return true
        int right = nums[0];
        for (int num : nums) {
            right = Math.max(right, num);
        }

        int left = 1;
        int res = right;
        while (left < right) {

            int mid = left + (right - left) / 2;
            if (minimumSizeFeasible(nums, mid, maxOperations)) {
                res = mid;
                right = mid;
            } else {
                left = mid + 1;
            }

        }

        return res;
    }

    public boolean minimumSizeFeasible(int[] nums, int threshold, int maxOperations) {
        int ops = 0;
        for (int n : nums) {
            ops += (n + threshold - 1) / threshold - 1;
            if (ops > maxOperations) return false;
        }
        return true;
    }
}
