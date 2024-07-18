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

        // 1. Keep track of the number of characters in the window
        Map<Character, Integer> windowMap = new HashMap<>();
        Map<Character, Integer> checkMap = new HashMap<>();
        for (Character chr : check.toCharArray()) {
            checkMap.merge(chr, 1, Integer::sum);
        }

        // 2. Keep track of the number of characters needed to satisfy the check
        int satisfyCondition = 0;
        int required = checkMap.size(); // SatisfyCondition should be same as required for it to be considered

        int left = 0;
        for (int right = 0; right < original.length(); right++) {
            Character currentChar = original.charAt(right);
            windowMap.put(currentChar, windowMap.getOrDefault(currentChar, 0));
            if (windowMap.get(currentChar).equals(checkMap.get(currentChar))) {
                satisfyCondition++;
            }

            // Decrease the map

        }


        return "";
    }

}
