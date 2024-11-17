package leetcode;

import java.util.*;

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

    // * 76. Minimum Window Substring
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public String minWindow(String original, String target) {
        if (original.length() < target.length()) {
            return "";
        }

        // 1. Keep track of each character in the "Check" and the occurences in a hashmap
        Map<Character, Integer> store = constructCheckMap(target);
        int check = store.keySet().size();
        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int boundary = 0;
        // 2. Keep track of a "Check", if Check == hashmap.keys == a sub string has been found
        // 3. From there move the right pointer forward in the original string, minus the number of occurences in check
        for (int right = 0; right < original.length(); right++) {

            Character currChar = original.charAt(right);
            if (store.containsKey(currChar)) {

                store.put(currChar, store.get(currChar) - 1);
                // Means check for this characcter is achieved
                if (store.get(currChar) == 0) {
                    check--;
                }
            }

            // Means a window has been found
            while (check == 0) {
                if (minLength > right - left + 1) {
                    boundary = left;
                    minLength = right - left + 1;
                }

                // Move left until check no longer 0
                Character leftChar = original.charAt(left);
                if (store.containsKey(leftChar)) {
                    if (store.get(leftChar) == 0) {
                        check++;
                    }
                    store.put(leftChar, store.get(leftChar) + 1);
                }
                left++;
            }
        }
        // 4. When value for the key reaches 0, means at that key is "checked", check ++
        // 5. Once check is matched, get the minLength, move the window up
        return (minLength == Integer.MAX_VALUE && check > 0) ? "" : original.substring(boundary, boundary + minLength);
    }

    public Map<Character, Integer> constructCheckMap(String check) {
        Map<Character, Integer> map = new HashMap<>();
        for (Character chr : check.toCharArray()) {
            map.put(chr, map.getOrDefault(chr, 0) + 1);
        }
        return map;
    }

    // * 2938. Separate Black and White Balls - Min number of swaps
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public long minimumSteps(String s) {
        /*
         *   This follows the partition step of quick sort algorithm, where we maintain two pointers and execute swaps
         *   Maintain a left and right pointer, and focus on a specific number - In the case it would be 0
         *   Swaps would only consist of the pointers moving, not the actual swaps (Expensive)
         */
        int left = 0;
        long count = 0;
        for (int right = 0; right < s.length(); right++) {
            // If right hits a '0', 'swap' it with the curr left pointer
            if (s.charAt(right) == '0') {
                count += (right - left);
                left++;
            }
        }
        return count;

        // * The above solution works as a one pass because it only contains two partition. If it was multiple
        // numbers to be sorted, it can go up to o (n ** 2) / o (n log n) - Basically same as quick sort`
    }

    // * 5. Longest Palindromic Substring
    // * Time complexity - o(n ** 2)
    // * Space complexity - o(1)
    public String longestPalindrome(String s) {
        String palindrome = "";

        int left;
        int right;
        for (int i = 0; i < s.length(); i++) {
            // Odd palindrome
            left = i;
            right = i;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > palindrome.length()) {
                    palindrome = s.substring(left, right + 1);
                }
                left--;
                right++;
            }
            // Even palindrome
            left = i;
            right = i + 1; // Handles for even
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > palindrome.length()) {
                    palindrome = s.substring(left, right + 1);
                }
                left--;
                right++;
            }
        }

        return palindrome;
    }

    // * 647. Palindromic Substrings
    // * Time complexity - o(n ** 2)
    // * Space complexity - o(1)
    public int countSubstrings(String s) {
        // 1. Similar approach to finding longest palindromic string
        // 2. Maintain two pointers at each and branch out to find the palindromes
        int palindromes = 0;
        int left;
        int right;
        for (int i = 0; i < s.length(); i++) {
            palindromes++; // Handles the current char as a palindrome

            // Handle odd number
            left = i - 1;
            right = i + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                palindromes++;
            }

            // Handle even number
            left = i;
            right = i + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                palindromes++;
            }
        }
        return palindromes;
    }

    // * 2161. Partition Array According to Given Pivot
    // * Time complexity: o(3n)
    // * Space complexity: o(n) -> Can be reduced to o(1) by using the same array and swapping
    public int[] pivotArray(int[] nums, int pivot) {
        int[] result = new int[nums.length];

        // 3 Seperate loops
        int less = 0;
        for (int num : nums) {
            if (num < pivot) {
                result[less] = num;
                less++;
            }
        }

        int middle = less;
        for (int num : nums) {
            if (num == pivot) {
                result[middle] = num;
                middle++;
            }
        }

        int more = middle;
        for (int num : nums) {
            if (num > pivot) {
                result[more] = num;
                more++;
            }
        }

        return result;
    }

    // * 3254. Find the Power of K-Size Subarrays I
    // * Time complexity: o(n)
    // * Space complexity: o(n);
    public int[] resultsArray(int[] nums, int k) {
        // 1. Create a result array - it will always be nums.length - k + 1
        int[] result = new int[nums.length - k + 1];

        // 2. Establish the first k elements as a window to find the consecutive numbers
        int consecutive = 1;
        for (int i = 0; i < k - 1; i++) {
            if (nums[i] + 1 == nums[i + 1]) {
                consecutive++;
            }
        }

        // 3. Iterate through the rest of the array
        int left = 0;
        for (int right = k; right < nums.length; right++) {
            // Check if the current window is consecutive
            if (consecutive == k) {
                result[left] = nums[right - 1];
            } else {
                result[left] = -1;
            }

            // Proceed to either increment or decrement consecutive count
            if (nums[right] - 1 == nums[right - 1]) {
                consecutive++;
            }

            if (nums[left] + 1 == nums[left + 1]) {
                consecutive--;
            }
            left++;
        }

        if (consecutive == k) {
            result[left] = nums[nums.length - 1];
        } else {
            result[left] = -1;
        }

        return result;
    }

    // * 209. Minimum Size Subarray Sum
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return (left == 0 && sum < target) ? 0 : Math.min(minLength, nums.length - left + 1);
    }
}
