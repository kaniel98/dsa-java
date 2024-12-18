package misc.twopointer;

import java.util.List;

// * Typically refer to questions where the pointers are moving in opposite directions
public class OppositeDirection {
    public static void main(String[] args) {

    }

    // * Two sum sorted
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static List<Integer> twoSumSorted(List<Integer> arr, int target) {
        // WRITE YOUR BRILLIANT CODE HERE
        int left = 0;
        int right = arr.size() - 1;

        while (left < right) {
            int currentSum = arr.get(left) + arr.get(right);
            if (currentSum == target) {
                return List.of(left, right);
            }
            if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }
        return List.of();
    }

    // * Valid palindrome
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        s = s.toLowerCase();
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right++;
            }
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right++;
        }
        return true;
    }
}
