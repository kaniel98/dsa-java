package leetcode;

import java.util.*;

public class BinarySearchQuestions {
    public static void main(String[] args) {
        int[] input = new int[]{4, 5, 6, 7, 0, 1, 2};
        BinarySearchQuestions test = new BinarySearchQuestions();
        System.out.println(test.searchInRotatedSortedArray(input, 0));
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
            if (isFeasible(piles, h, mid)) {
                result = mid;
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


}
