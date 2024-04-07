package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {
    public static void main(String[] args) {
        System.out.println(TwoPointers.isPalindrome("A man, a plan, a canal: Panama"));
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
    public List<List<Integer>> threeSum(int[] nums) {
        // Sort the arrays
        Arrays.sort(nums); // n log n
        List<List<Integer>> result = new ArrayList<>();

        if (nums.length < 3) {
            return result;
        }

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int current = nums[i];
            int left = i + 1; // No need to look behind - possible combinations for earlier numbers would have been identified
            int right = nums.length - 1;

            while (left < right) {
                int currentSum = nums[left] + nums[right] + current;
                if (currentSum > 0) {
                    right--;
                } else if (currentSum < 0) {
                    left++;
                } else {
                    result.add(List.of(current, nums[left], nums[right]));
                    // increase left pointer
                    left++; // Prevent left from being reused
                    while (nums[left] == nums[left - 1] && left < right) {
                        left++; // Ensures left will still avoid duplicate
                    }
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

}
