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

    // * 2109. Adding Spaces to a String
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    // * Note: Question can also be solved just by initiating an array with the size of s + spaces.length and
    // creating a string based off that array
    public String addSpaces(String s, int[] spaces) {
        // Maintain a string builder
        // Maintain a pointer moving through S
        // Everytime the pointer meets the same index as spaces, add space to sb
        // Return sb towards the end.

        StringBuilder sb = new StringBuilder();
        int curr = 0;
        for (int i = 0; i < s.length(); i++) {
            if (curr < spaces.length && i == spaces[curr]) {
                // Add space to the string builder
                sb.append(" ");
                curr++;
            }
            // continue by adding the char at i back to sb;
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    // * 848. Shifting Letters
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public String shiftingLetters(String s, int[] shifts) {
        // 1. Get the sum of all shifts
        // 2. Apply the sum from back to the start to the end, removing shift each time
        int totalShifts = 0;
        StringBuilder sb = new StringBuilder();
        // We will need to remove the number of times it goes beyond 26 - number of char
        for (int shift : shifts) {
            totalShifts = (totalShifts + shift) % 26; // This is to prevent overflow
        }

        for (int idx = 0; idx < s.length(); idx++) {
            sb.append(getShiftedLetter(s.charAt(idx), totalShifts));
            totalShifts = Math.floorMod(totalShifts - shifts[idx], 26);
        }

        return sb.toString();
    }

    public char getShiftedLetter(char chr, int shift) {
        chr -= 'a';
        return (char) ((chr + shift) % 26 + 97);
    }

    // * 2381. Shifting Letters II - Note this can also be a prefix question
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public String shiftingLetters(String s, int[][] shifts) {
        // Prefix question
        // It is easier to handle the string once it is converted into an array instead
        // 1. Maintain a prefix array that is + 1 of the String s
        // 2. Keep track from the start to end, is it plus 1 or minus 1
        char[] charArray = s.toCharArray();
        int[] prefix = new int[charArray.length + 1];

        // Populate the prefix with the shifts first
        for (int[] shift : shifts) {
            int start = shift[2] == 1 ? 1 : -1; // If shift[2] is 1, we will shift the letters by + 1 else - 1
            int end = shift[2] == 1 ? -1 : 1; // This would be to reset the array

            // Mark the start and end of the affected section in the prefix section
            prefix[shift[1] + 1] += start;
            prefix[shift[0]] += end;
        }

        // Proceed to make the increment/decrement
        // Value + 25, Followed by moding it by 26 to ensure it is always within the range of 0 - 25
        int val = prefix[prefix.length - 1];
        for (int idx = charArray.length - 1; idx >= 0; idx--) {
            // Apply the operations to the char array;
            val = (val % 26 + 26) % 26; // Prevents overflow
            char temp = (char) (((charArray[idx] - 'a' + val) % 26) + 'a');

            // Include the increment and decrement marked at the current idx
            val += prefix[idx];
            charArray[idx] = temp;
        }

        return String.valueOf(charArray);
    }
}
