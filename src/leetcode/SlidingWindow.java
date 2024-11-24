package leetcode;

import java.util.*;

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
            Arrays.sort(new Integer[]{});
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
    // * Time complexity - o (N)
    // * Space complexity - o (1)
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

    // * 1838. Frequency of the most frequent element
    // * Time complexity - o (n log n)
    // * Space complexity - o (n)
    public int maxFrequency(int[] nums, int k) {
        // 1. Sort the array to group similar numbers together
        Arrays.sort(nums);
        int left = 0;
        long total = 0; // Refers to the sum of the elements
        long res = 0;
        for (int right = 0; right < nums.length; right++) {
            total += nums[right]; // Keep track of current sum
            // Condition keeps track of the total possible sum in the window required for all elements to be of same
            // size compared to the total values available (total + k)
            while ((nums[right] * (right - left + 1L) - total > k)) {
                total -= nums[left];
                left++;
            }
            res = Math.max(res, right - left + 1L);
        }
        return (int) res;
    }

    // * 904. Fruits into baskets
    // * Time complexity -
    // * Space complexity -
    public int totalFruit(int[] fruits) {
        // 1. Maintain left and right pointer & total variable
        // 2. Maintain hashmap to keep track of the type and number of fruits
        // 3. Move right pointer and add into the "total" variable until a 3rd distinct has been found
        // 4. At this point, remove until only two distinct is left
        // 5. Get the max at all time and return it

        int left = 0;
        int maxFruits = 0;
        int current = 0;
        HashMap<Integer, Integer> fruitCount = new HashMap<>();
        for (int right = 0; right < fruits.length; right++) {
            current++;
            fruitCount.put(fruits[right], fruitCount.getOrDefault(fruits[right], 0) + 1);

            while (fruitCount.size() > 2) {
                int firstDistinctFruit = fruits[left];
                fruitCount.put(firstDistinctFruit, fruitCount.get(firstDistinctFruit) - 1);
                current--;
                left++;

                if (fruitCount.get(firstDistinctFruit) <= 0) {
                    fruitCount.remove(firstDistinctFruit);
                }
            }
            maxFruits = Math.max(maxFruits, current);
        }
        return maxFruits;
    }

    // * 2461. Maximum Sum of Distinct Subarrays With Length K
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public long maximumSubarraySum(int[] nums, int k) {
        // 1. HashMap to keep track of characters and its index
        // 2. Left and Right pointer to keep track of the characters that appear
        // if a duplicate number is found, simply move left until right
        Map<Integer, Integer> idxMap = new HashMap<>();
        int left = 0;
        long max = 0;
        long curr = 0;

        for (int right = 0; right < nums.length; right++) {
            if (idxMap.containsKey(nums[right])) {
                // Move left until it is the index of nums[right];
                int target = idxMap.get(nums[right]);
                while (left <= target) {
                    curr -= nums[left];
                    idxMap.remove(nums[left]);
                    left++;
                }
            }

            idxMap.put(nums[right], right);
            curr += nums[right];

            // Move left up
            while (idxMap.size() > k) {
                curr -= nums[left];
                idxMap.remove(nums[left]);
                left++;
            }

            if (idxMap.size() == k) {
                max = Math.max(max, curr);
            }
        }

        return max;
    }

    // * 2516. Take K of Each Character From Left and Right
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public int takeCharacters(String s, int k) {
        // Instead focusing on finding the max window on both sides, it would be easier to find the maximum inner window
        // This would take the example of finding the non overlapping areas of two circles
        // It is easier to take circle 1 - circle 2

        if (k == 0) {
            return 0;
        }

        // 1. Get the total count of characters from A-B-C
        int[] charCount = new int[3];
        for (char chr : s.toCharArray()) {
            charCount[chr - 'a']++;
        }

        int conditionMet = 0;
        for (int count : charCount) {
            if (count >= k) {
                conditionMet++;
            }
        }

        if (conditionMet < 3) {
            return -1; // No Possible case
        }

        // 2. Use left and right pointers to check for windows which would invalidate the whole count
        int left = 0;
        int max = 0;
        for (int right = 0; right < s.length(); right++) {
            charCount[s.charAt(right) - 'a']--;

            if (charCount[s.charAt(right) - 'a'] < k) { // Means a window is found
                max = Math.max(right - left, max);

                while (charCount[s.charAt(right) - 'a'] < k) {
                    charCount[s.charAt(left) - 'a']++;
                    left++;
                }
            }
        }
        max = Math.max(max, s.length() - left);

        return max == 0 ? s.length() : s.length() - max;
    }

    // * 1423. Maximum Points You Can Obtain from Cards
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public int maxScore(int[] cardPoints, int k) {
        int totalScore = Arrays.stream(cardPoints).sum();

        // Use a sliding window to find the minimum window size instead
        int min = 0;
        for (int i = 0; i < cardPoints.length - k; i++) {
            min += cardPoints[i];
        } // Starting window


        int currWindow = min;
        int left = 0;
        for (int right = cardPoints.length - k; right < cardPoints.length; right++) {
            currWindow += cardPoints[right];
            currWindow -= cardPoints[left];
            left++;
            min = Math.min(currWindow, min);
        }

        return totalScore - min;
    }

    // * 187. Repeated DNA Sequences
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> existingSequence = new HashSet<>();
        Set<String> result = new HashSet<>();

        // Base case;
        if (s.length() < 10) {
            return new ArrayList<>();
        }

        for (int i = 0; i < s.length(); i++) {
            if (i + 10 > s.length()) {
                continue;
            }
            String curr = s.substring(i, i + 10);

            if (existingSequence.contains(curr)) {
                result.add(curr);
            } else {
                existingSequence.add(curr);
            }
        }

        return new ArrayList<>(result);
    }
}
