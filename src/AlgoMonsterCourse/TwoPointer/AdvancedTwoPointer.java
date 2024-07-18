package AlgoMonsterCourse.TwoPointer;

import java.util.HashMap;
import java.util.Map;

public class AdvancedTwoPointer {
    public static void main(String[] args) {

    }

    // * Minimum Window Substring
    // * Time complexity -
    // * Space complexity -
    public static String getMinimumWindow(String original, String check) {
        // 3. Number of characters in window must match the count and character - Keep track of char
        // 4. Afterwards, check each subsequent window by moving left pointer until satisfy does not match
        // 5. After satisfy does not match, repeat the full process of moving right pointer till it satisfy again

        // 1. Keep track of the number of characters in check
        Map<Character, Integer> checkMap = new HashMap<>();
        for (Character chr : check.toCharArray()) {
            checkMap.put(chr, checkMap.getOrDefault(chr, 0) + 1);
        }

        // 2. Keep track of the number of characters that matches the check
        int matched = 0;
        int left = 0;
        int minLength = original.length() + 1;
        int window = 0;

        for (int right = 0; right < original.length(); right++) {
            char currentChar = original.charAt(right);
            if (checkMap.containsKey(currentChar)) {
                checkMap.put(currentChar, checkMap.get(currentChar) - 1); // Remove it
                if (checkMap.get(currentChar) == 0) matched++; // If 0, means it satisfy the condition
            }

            // checkMap.size is the number of matches we need
            // At this point, we are simply reducing until it doesnt match, so that we can proceed to find the next
            // substring that matches
            while (matched == checkMap.size()) {
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1; // Keeps track of the minimum length
                    window = left; // Moves the new window to the current left pointer
                }

                char charToBeDeleted = original.charAt(left);
                left++;
                if (checkMap.containsKey(charToBeDeleted)) {
                    // Add it back
                    if (checkMap.get(charToBeDeleted) == 0) matched--;
                    checkMap.put(charToBeDeleted, checkMap.get(charToBeDeleted) + 1);
                }
            }
        }

        // If minLenght is bigger thn original length, means no match is found
        // Else return the min point
        return minLength > original.length() ? "" : original.substring(window, window + minLength);
    }

}
