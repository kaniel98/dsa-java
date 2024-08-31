package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {
    public static void main(String[] args) {
        System.out.println(TwoPointers.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    // * 125 Valid Palindrome
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            if (!Character.isLetterOrDigit(leftChar)) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(rightChar)) {
                right--;
                continue;
            }
            if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // * 167 Two Sum II - Input Array is sorted
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        if (numbers == null || numbers.length < 2) {
            return result;
        }
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int currentSum = numbers[left] + numbers[right];
            if (currentSum == target) {
                result[0] = left + 1;
                result[1] = right + 1;
                break;
            }
            if (currentSum > target) {
                right--;
            }
            if (currentSum < target) {
                left++;
            }
        }
        return result;
    }

    // * 15. 3sum
    // * Time complexity - O(N ** 2 + n log n) --> O(N**2)
    // * Space complexity - O(1) (If we exclude sorting - We do not include sorting space)
    public static List<List<Integer>> threeSum(int[] nums) {
        // 1. Sort the array (Smallest to largest)
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        // 2. For each element in the array, fix it and execute the two sum problem on the remaining array
        for (int anchor = 0; anchor < nums.length; anchor++) {
            // 3. If the current element is the same as the prev element, skip it.
            if (anchor > 0 && nums[anchor] == nums[anchor - 1]) {
                continue;
            }

            // * Execute two sum
            int left = anchor + 1; // Move to next eleemnt
            int right = nums.length - 1;
            int target = -nums[anchor];

            while (left < right) {
                int currentSum = nums[left] + nums[right];
                if (currentSum > target) { // Too big
                    right--;
                } else if (currentSum < target) {// Too small
                    left++;
                } else {
                    // 4. For every match, add it to a result list
                    result.add(List.of(nums[anchor], nums[left], nums[right]));
                    // Increase left and right until they are both diff integers
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    // * 11. Container with most water
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxVolume = 0;

        while (left < right) {
            int currMaxArea = (right - left) * Math.min(height[left], height[right]);
            maxVolume = Math.max(maxVolume, currMaxArea);
            if (height[left] < height[right]) {
                left++;
            } else {
                right++;
            }
        }
        return maxVolume;
    }

    // * 1498. Number of Subsequences That Satisfy the Given Sum Condition
    // * Time complexity
    // * Space complexity
    public int numSubseq(int[] nums, int target) {
        // Key point: Regardless of if the array was sorted or not, the number of sub-seqeunce will remain the same
        return 0;
    }
}
