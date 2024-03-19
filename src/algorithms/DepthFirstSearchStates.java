package algorithms;

import java.util.*;

// Practices involving Depth First Search with States (Backtracking)
public class DepthFirstSearchStates {
    public static void main(String[] args) {
    }

    public static List<String> ternaryTreePaths(Node<Integer> root) {
        // WRITE YOUR BRILLIANT CODE HERE
        return List.of();
    }

    // Get all possible paths for a ternary tree
    // Time complexity - o(n)
    // Space complexity - o(h) number of function calls + o(n**2) number of arrays
    public static void dfsAllPossiblePaths(Node<Integer> root, ArrayList<String> path, ArrayList<String> res) {
        // Case 1: When the node has no more children
        if (root.children.isEmpty()) {
            path.add(Integer.toString(root.val)); // Add the current node
            res.add(String.join("->", path)); // Add the current path to result
            return; // Break the current loop
        }

        // Case 2: Proceed to call DFS on each node
        for (Node<Integer> child : root.children) {
            // Create a new copy for each path of the tree
            ArrayList<String> newPath = new ArrayList<>(path);
            // Proceed to add the next current node into the path
            newPath.add(Integer.toString(root.val));
            dfsAllPossiblePaths(child, newPath, res);
        }
    }

    // Inefficient to continuously create new array - Use a stack instead
    public static void dfsAllPossiblePathWithStack(Node<Integer> root, ArrayList<String> path, ArrayList<String> res) {
        // Case 1: When the root has no more children
        if (root.children.isEmpty()) {
            path.add(Integer.toString(root.val)); // Add the current node
            res.add(String.join("->", path)); // Add the current path to result
            path.remove(path.size() - 1);
            return;
        }

        for (Node<Integer> child : root.children) {
            if (child != null) {
                path.add(Integer.toString(root.val)); // Add the current root to the path
                dfsAllPossiblePathWithStack(child, path, res);
                path.remove(path.size() - 1); // Remove the current one since it is no longer required
            }
        }
    }

    // * ########################################################################

    public static List<String> ternaryTreePath(Node<Integer> root) {
        ArrayList<String> result = new ArrayList<>();
        if (root.val == null) {
            return result;
        }
        ArrayList<String> currentPath = new ArrayList<>();
        dfsAllPossiblePaths(root, currentPath, result);
        return result;
    }

    public static List<String> letterCombination(int n) {
        List<String> result = new ArrayList<>();
        letterCombinationDfs(result, new ArrayList<>(), 0, n);

        return result;
    }

    private static void letterCombinationDfs(List<String> result, List<String> currentPath, int startIndex, int n) {
        if (startIndex == n) {
            result.add(String.join("", currentPath));
            return;
        }

        for (String letter : new String[]{"A", "B"}) {
            currentPath.add(letter);
            letterCombinationDfs(result, currentPath, startIndex + 1, n);
            currentPath.remove(startIndex);
        }
    }

    // * ########################################################################
    // Time complexity - O(4^n) where n is the number of digits in the input (4 because worse case its 4 char)
    // Space complexity - O(n) where n is the number of digits in the input
    private static void generateAllPossibleCombinationsFromAPhone(List<String> result, String phoneDigits, int currentIndex, StringBuilder path) {
        if (currentIndex > phoneDigits.length() - 1) {
            result.add(path.toString());
            return;
        }

        for (Character character : KEYBOARD.get(phoneDigits.charAt(currentIndex))) {
            path.append(character);
            generateAllPossibleCombinationsFromAPhone(result, phoneDigits, currentIndex + 1, path);
            path.deleteCharAt(path.length() - 1);
        }
    }

    private static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        generateAllPossibleCombinationsFromAPhone(result, digits, 0, new StringBuilder());
        return result;
    }

    // * ########################################################################
    // Time complexity: O(2^N) Either include character in partition or start new - Each call will potentially double the number of recursive calls. Each call need to be O(n) as well because we want to see if palindrome anot
    // Space complexity: O(n) height of the tree
    public static void generateAllPartitionsDfs(List<List<String>> result, List<String> currentPartition, int startIndex, String s) {
        // Return if start index and original string same length
        if (startIndex == s.length()) {
            result.add(new ArrayList<>(currentPartition));
            return;
        }

        // Else iterate through
        for (int end = startIndex; end < s.length(); end++) {
            // Check if current is palindrome
            if (!isPalindrome(s.substring(startIndex, end + 1))) {
                continue;
            }
            // Proceed to add and remove accordingly
            currentPartition.add(s.substring(startIndex, end + 1));
            generateAllPartitionsDfs(result, currentPartition, end + 1, s);
            currentPartition.remove(currentPartition.size() - 1);
        }
    }

    public static boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r))
                return false;
            l++;
            r--;
        }
        return true;
    }

    public static List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        generateAllPartitionsDfs(ans, new ArrayList<>(), 0, s);
        return ans;
    }

    private static final Map<Character, char[]> KEYBOARD = Map.of(
            '2', "abc".toCharArray(),
            '3', "def".toCharArray(),
            '4', "ghi".toCharArray(),
            '5', "jkl".toCharArray(),
            '6', "mno".toCharArray(),
            '7', "pqrs".toCharArray(),
            '8', "tuv".toCharArray(),
            '9', "wxyz".toCharArray()
    );

    // * ########################################################################
    // 1. Check if the current string iteration has valid parenthesis
    private static boolean isValidParenthesis(String string) {
        // Initialize a stack
        Deque<Character> stack = new ArrayDeque<>();
        for (Character character : string.toCharArray()) {
            if (character.equals('(')) {
                stack.push(')');
            } else if (stack.size() == 0) {
                return false;
            } else {
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    // Time complexity: O(4^n) combinations + o(2n) string length = O(4^n * n)
    // Space complexity: o(4^n  n
    public static void generateParenthesesDfs(List<String> result, List<String> currentPath, int n, int openCount, int closeCount) {
        if (currentPath.size() == n * 2) {
            String formedParenthesis = String.join("", currentPath);
            if (isValidParenthesis(formedParenthesis)) {
                result.add(formedParenthesis);
            }
            return;
        }
        // Proceed to go down each path, add either ( or ) to it
        if (openCount < n) { // Means that the current number of open is less than close
            currentPath.add("(");
            generateParenthesesDfs(result, currentPath, n, openCount + 1, closeCount);
            currentPath.remove(currentPath.size() - 1);
        }
        if (closeCount < openCount) {
            currentPath.add(")");
            generateParenthesesDfs(result, currentPath, n, openCount, closeCount + 1);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    // * ########################################################################
    public static List<String> permutations(String letters) {
        List<String> result = new ArrayList<>();
        generateAllPermutationDfs(result, new ArrayList<>(), 0, letters, new HashMap<>());
        return result;
    }

    // Time complexity - o(n!)
    // Space complexity - O(n!)
    private static void generateAllPermutationDfs(List<String> result, List<String> currentCombination, int startIndex, String letters, Map<Character, Boolean> isCharacterUsed) {
        // Base case
        if (startIndex == letters.length()) {
            result.add(String.join("", currentCombination));
            return;
        }

        for (Character character : letters.toCharArray()) {
            // If not empty && the current character at the end is the same, skip
            if (isCharacterUsed.getOrDefault(character, false)) {
                continue;
            }
            currentCombination.add(String.valueOf(character));
            isCharacterUsed.put(character, true);
            generateAllPermutationDfs(result, currentCombination, startIndex + 1, letters, isCharacterUsed);
            currentCombination.remove(currentCombination.size() - 1);
            isCharacterUsed.put(character, false);
        }
    }

    // * ########################################################################
    public static boolean wordBreak(String s, List<String> words) {
        return false;
    }

    // Without memoization - o (m ^ n) where m is all words to construct the strict and n is the length of the string
    public static boolean wordBreakDfs(int startIndex, String target, String[] words) {
        if (startIndex == words.length) return true;

        boolean ans = false;
        for (String word : words) {
            if (target.substring(startIndex).startsWith(word)) {
                ans = ans || wordBreakDfs(startIndex + word.length(), target, words);
            }
        }
        return ans;
    }

    // With memoization - Using a memorization to keep track of the possible path
    // Time complexity - o(n^2 * M) because no longer need to continuously check for each word - Memo
    // Space complexity - o(n)
    private static boolean wordBreakDfsMemoization(int startIndex, String target, String[] words, Boolean[] memo) {
        if (startIndex == words.length) return true;
        if (memo[startIndex] != null) return memo[startIndex];

        boolean ans = false;
        for (String word : words) {
            if (target.substring(startIndex).startsWith(word)) {
                ans = ans || wordBreakDfs(startIndex + word.length(), target, words);
            }
        }
        // If we are able to match words all the way up to i, but then fail to find
        // any solutions from there with our words, then we will *always* fail to find
        // a match if we arrive at this length again (because all words can be reused
        // as often as you want, and we've already tried everything from this point).
        //
        // If we back up to a previous position, it's possible we will find a match that
        // places us at a new length i, from which one of our words might be able to match
        // to the end.
        memo[startIndex] = ans;
        return ans;
    }

    // * ########################################################################
    public static int decodeWays(String digits) {
        return decodeWaysDfs(0, digits, new int[digits.length()]);
    }

    // Without memoization - Time complexity is o(n**2)
    // With memoization - Time complexity is o(n)
    // Space complexity remains at o(n) - Will only go to the length of the digits at most
    private static int decodeWaysDfs(int startIndex, String digits, int[] memo) {
        if (startIndex == digits.length()) {
            return 1;
        }
        if (memo[startIndex] != -1) return memo[startIndex];

        int ways = 0;
        if (digits.charAt(startIndex) == '0') return ways;
        // Decode one digit
        ways += decodeWaysDfs(startIndex + 1, digits, memo);
        // Decode two digit
        // Condition to make sure not more than index & the next two is less than 26
        if (startIndex + 2 <= digits.length() && Integer.parseInt(digits.substring(startIndex, startIndex + 2)) <= 26) {
            ways += decodeWaysDfs(startIndex + 2, digits, memo);
        }
        memo[startIndex] = ways;

        return ways;
    }

    // * ########################################################################
    public static int coinChange(List<Integer> coins, int amount) {
        // WRITE YOUR BRILLIANT CODE HERE
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -1);
        int result = coinChangeDfs(coins, amount, 0, memo);
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static int coinChangeDfs(List<Integer> coins, int amount, int sum, int[] memo) {
        if (amount == sum) {
            return 0; // Means a leaf has been found
        }

        if (sum > amount) {
            // Means out of bound, invalid this case
            return Integer.MAX_VALUE;
        }

        if (memo[sum] != -1) {
            return memo[sum];
        }

        int ans = Integer.MAX_VALUE;
        // Go down each node
        for (Integer coin : coins) {
            int result = coinChangeDfs(coins, amount, sum + coin, memo);
            // If the dfs returns max value, means that path is invalid
            if (result == Integer.MAX_VALUE) {
                continue;
            }
            ans = Math.min(ans, result + 1); // Add one to accommodate for current
        }

        // Each branch we are saving the minimum of each
        // Thus reducing duplicate traversals
        memo[sum] = ans;
        return ans;
    }

    public static class Node<T> {
        public T val;
        public List<Node<T>> children;

        public Node(T val) {
            this(val, new ArrayList<>());
        }

        public Node(T val, List<Node<T>> children) {
            this.val = val;
            this.children = children;
        }
    }


}
