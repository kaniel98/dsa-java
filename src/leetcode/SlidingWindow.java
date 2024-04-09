package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindow {
    // * 3. Longest substring without repeating characters
    // * Time complexity: o(n) - Iterate through each character once only
    // * Space complexity; o(n) - Max size of n hashset
    public int lengthOfLongestSubstring(String s) {
        // Hashmap to keep track of character and its corresponding position
        HashMap<Character, Integer> positionMap = new HashMap<>();
        // Initialize two pointers - left and right
        int left = 0;
        int longestSubString = 0;

        for (int right = 0; right < s.length(); right++) {
            Character currentCharacter = s.charAt(right);
            if (positionMap.containsKey(currentCharacter)) {
                longestSubString = Math.max(longestSubString, right - left);
                if (positionMap.get(currentCharacter) + 1 > left) {
                    left = positionMap.get(currentCharacter) + 1;
                }
            }
            positionMap.put(currentCharacter, right);
        }
        return Math.max(longestSubString, s.length() - left);
    }

    // * 121. Best Time to Buy and Sell stock
    // * Time Complexity - o(n)
    // * Space complexity - o(1)
    public int maxProfit(int[] prices) {
        int left = 0;
        int right = 1;
        int profit = 0;
        while (right < prices.length) {
            if (prices[left] < prices[right]) {
                profit = Math.max(profit, prices[right] - prices[left]);
            } else {
                left = right; // Move it to the right pointer
            }
            right++;
        }
        return profit;
    }

    // * 424 Longest repeating character replacement
    // * Time complexity - O(N) (Checking every character once)
    // * Space complexity - O(1) (Size of hashmap will always be max 26)
    public int characterReplacement(String s, int k) {
        // Sliding Window Question
        // For the given window, keep track of the most frequent element
        // If Window size - Most frequent element <= k, thn see if it is bigger and adjust
        // If it is smaller than k, means not good, need to increase left pointer;
        Map<Character, Integer> store = new HashMap<>();
        int max = Integer.MIN_VALUE; // Most frequent element
        int ans = Integer.MIN_VALUE; // Longest string
        int left = 0; // Left pointer
        for (int right = 0; right < s.length(); right++) {
            // Add the current character to hashmap first
            store.put(s.charAt(right), store.getOrDefault(s.charAt(right), 0) + 1);
            // Do the check
            max = Math.max(max, Collections.max(store.values()));
            while ((right - left + 1 - max) > k) { // Means not valid, need to shift pointer
                store.put(s.charAt(left), store.get(s.charAt(left)) - 1); // Reduce count
                left++; // Increase left pointer
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    // * 567. Permutation in String
    public boolean checkInclusion(String s1, String s2) {
        // Get the Length of the String and Establish a hashmap of it
        // Use left and right pointer to navigate through string 2 - Window size will be the difference
        // Everytime left and right pointer moves, if character not in key, keep moving
        int windowSize = s1.length();
        int[] stringOneCharacters = new int[26];
        int[] stringTwoCharacters = new int[26];
        // Put s1 into stringOne;
        for (Character chr : s1.toCharArray()) {
            stringOneCharacters[chr - 'a']++;
        }
        int left = 0;
        // Put the first window into the hashmap first
        for (int right = 0; right < s2.length(); right++) {
            stringTwoCharacters[s2.charAt(right) - 'a']++;
            if (right >= windowSize) {
                stringTwoCharacters[s2.charAt(left) - 'a']--;
                left++;
            }
            if (Arrays.equals(stringOneCharacters, stringTwoCharacters)) {
                return true;
            }
        }
        return false;
    }
}
