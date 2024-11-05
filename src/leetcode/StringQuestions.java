package leetcode;

public class StringQuestions {
    public static void main(String[] args) {

    }

    // * 1957. Delete Characters to Make Fancy String
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public String makeFancyString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i < 2) {
                sb.append(s.charAt(i));
                continue; // Skip
            }
            if (s.charAt(i) == s.charAt(i - 1) && s.charAt(i) == s.charAt(i - 2)) {
                continue; // Skip this
            }
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    // * 2490. Circular Sentence
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public boolean isCircularSentence(String sentence) {
        // Check the first character and last character first
        if (sentence.charAt(0) != sentence.charAt(sentence.length() - 1)) {
            return false; // Immediately know this isn't a circular sentence
        }

        // Assuming there will only be one space between each word, iterate through and check the char before and after a space
        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) != ' ') {
                continue;
            }

            if (sentence.charAt(i - 1) != sentence.charAt(i + 1)) {
                return false;
            }
        }

        return true;
    }

    // * 796. Rotate String
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public boolean rotateString(String s, String goal) {
        // 1. Every possible way of forming the goal would be combining s with itself e.g., abc -> abcabc
        // 2. Next we would just check if goal is a substring within this goal
        // 3. Return true or false accordingly
        if (s.length() != goal.length()) {
            return false;
        }
        String possibleCombinations = s + s;
        return possibleCombinations.contains(goal);
    }

    // * 3019. Number of changing keys
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int countKeyChanges(String s) {
        int keyChange = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            // Compare the current one with the next char
            char chr = s.charAt(i);
            if (chr >= 97) { // Small letter
                if (chr == s.charAt(i + 1) || chr - 32 == s.charAt(i + 1)) {
                    continue;
                }
            }

            if (chr < 97) {
                if (chr == s.charAt(i + 1) || chr + 32 == s.charAt(i + 1)) {
                    continue;
                }
            }
            keyChange++;
        }
        return keyChange;
    }
}
