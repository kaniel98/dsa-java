package AlgoMonsterCourse.TwoPointer;

import java.util.*;

public class SlidingWindow {
    public static void main(String[] args) {
        System.out.println(SlidingWindow.longestSubstringWithoutRepeatingCharacters("abcdbea"));
    }

    // * Subarray sum fixed
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static int subarraySumFixed(List<Integer> nums, int k) {
        // Note array cannot be sorted, it is fixed
        int currSum = 0;
        int left = 0;
        for (int i = 0; i < k; i++) {
            currSum += nums.get(i);
        }
        int maxSum = currSum;
        for (int right = k; right < nums.size(); right++) {
            currSum -= nums.get(left);
            currSum += nums.get(right);
            maxSum = Math.max(currSum, maxSum);
            left++;
        }
        return maxSum;
    }

    // * All Anagrams in a string
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static List<Integer> findAllAnagrams(String original, String check) {
        // 1. Put the check into a hash map
        int[] checkCounter = new int[26];
        int[] window = new int[26];
        int checkLen = check.length();
        List<Integer> result = new ArrayList<>();

        if (original.length() < check.length()) {
            return result;
        }

        // 2. Initialize two pointers - Left and Right
        int left = 0;

        // Put the first len characters into the window first
        for (int i = 0; i < checkLen; i++) {
            // Setting the check
            checkCounter[check.charAt(i) - 'a']++;
            // Setting the current window
            window[original.charAt(i) - 'a']++;
        }
        if (Arrays.equals(window, checkCounter)) {
            result.add(0);
        }

        // 3. Put right at the length of the check, move the sliding window
        // 4. Everytime the character length matches + the hashmap matches, add the left pointer to the list
        for (int right = checkLen; right < original.length(); right++) {
            window[original.charAt(right) - 'a']++;
            window[original.charAt(left) - 'a']--;
            left++;
            if (Arrays.equals(window, checkCounter)) {
                result.add(left);
            }
        }
        return result;
    }

    // * Longest sub-array sum
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static int subarraySumLongest(List<Integer> nums, int target) {
        // WRITE YOUR BRILLIANT CODE HERE
        int currentSum = 0;
        int maxSize = 0;
        int left = 0;
        for (int right = 0; right < nums.size(); right++) {
            currentSum += nums.get(right);
            while (currentSum > target) {
                currentSum -= nums.get(left);
                left++;
            }
            maxSize = Math.max(maxSize, right - left + 1);
        }
        return maxSize;
    }

    // * Longest substring without repeating characters
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public static int longestSubstringWithoutRepeatingCharacters(String s) {
        // 1. Initialize hashmap to keep track of the characters position
        // 2. Initialize left and right pointer
        // 3. For every characcter, add it to the hashmpa (Using right as pointer)
        // 4. If the character has more than one occurence in the hashmap, immediately pop it and move the left
        // pointer to it + 1
        // Get the max value, return it at the end
        Map<Character, Integer> characterIntegerMap = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            Character currentChar = s.charAt(right);

            if (characterIntegerMap.containsKey(currentChar) && characterIntegerMap.get(currentChar) + 1 > left) {
                left = characterIntegerMap.get(currentChar) + 1;
            }
            characterIntegerMap.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return Math.max(maxLength, s.length() - left);
    }

    // * Shortest Sub array sum
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static int subarraySumShortest(List<Integer> nums, int target) {
        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int currSum = 0;
        for (int right = 0; right < nums.size(); right++) {
            currSum += nums.get(right);
            while (currSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currSum -= nums.get(left);
                left++;
            }
        }
        return minLength;
    }

    // * Least consecutive cards to match
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public static int leastConsecutiveCardsToMatch(List<Integer> cards) {
        Map<Integer, Integer> cardIndexMap = new HashMap<>();
        int minLength = Integer.MAX_VALUE;
        for (int right = 0; right < cards.size(); right++) {
            Integer currentCard = cards.get(right);
            if (cardIndexMap.containsKey(currentCard)) {
                minLength = Math.min(minLength, right - cardIndexMap.get(currentCard) + 1);
            }
            cardIndexMap.put(currentCard, right);
        }
        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }

}
