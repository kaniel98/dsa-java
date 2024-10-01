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
            ;

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
}
