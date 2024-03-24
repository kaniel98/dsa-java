import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }

    public static String longestPalindrome(String s) {
        String currentLongest = "";
        return longestPalindromeDfs(currentLongest, 0, s, new HashMap<>());

    }

    // Function to check if it is palindrome
    private static boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r))
                return false;
            l++;
            r--;
        }
        return true;
    }

    private static String longestPalindromeDfs(String currentLongest, int startIndex, String s, Map<Integer, String> currentMap) {
        if (startIndex == s.length()) {
            return currentLongest;
        }
        if (currentMap.getOrDefault(startIndex, null) != null) {
            return currentMap.get(startIndex);
        }
        for (int end = startIndex; end < s.length(); end++) {
            if (!isPalindrome(s.substring(startIndex, end + 1))) {
                continue;
            }
            // Else check if it is the current longest
            if (currentLongest.length() < s.substring(startIndex, end + 1).length()) {
                currentLongest = s.substring(startIndex, end + 1);
            }
            // Proceed to go down further
            String result = longestPalindromeDfs(currentLongest, end + 1, s, currentMap);
            if (result.length() > currentLongest.length()) {
                currentLongest = result;
            }
        }
        // for the current start index set the longest
        currentMap.put(startIndex, currentLongest);
        return currentLongest;
    }
}