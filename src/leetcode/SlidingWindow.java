package leetcode;

import java.util.HashMap;

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
}
