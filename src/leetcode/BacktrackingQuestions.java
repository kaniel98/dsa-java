package leetcode;

import java.util.*;

public class BacktrackingQuestions {
    public static void main(String[] args) {
    }

    // * 78. Subsets
    // Given an integer array nums of unique elements, return all possible
    //subsets (the power set).
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        subsetsHelper(nums, new ArrayList<>(), result, 0, nums.length);
        return result;
    }

    private void subsetsHelper(int[] nums, List<Integer> currentSet, List<List<Integer>> result, int currPointer, int numsLen) {
        if (currPointer > numsLen) {
            return;
        }
        result.add(new ArrayList<>(currentSet));
        for (int i = currPointer; i < numsLen; i++) {
            currentSet.add(nums[i]);
            subsetsHelper(nums, currentSet, result, i + 1, numsLen);
            currentSet.removeLast();
        }
    }

    // * 39. Combination Sum
    // Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        System.out.println(Arrays.toString(candidates));
        combinationSumHelper(candidates, target, 0, new ArrayList<>(), result, 0);
        return result;
    }

    private void combinationSumHelper(int[] candidates, int target, int currentSum,
                                      List<Integer> currentSumCandidates, List<List<Integer>> result,
                                      int currentPointer) {
        if (currentSum == target) {
            result.add(new ArrayList<>(currentSumCandidates));
            return;
        }
        if (currentSum > target) {
            return;
        }
        for (int i = currentPointer; i < candidates.length; i++) {
            currentSumCandidates.add(candidates[i]);
            combinationSumHelper(candidates, target, currentSum + candidates[i], currentSumCandidates, result, i);
            currentSumCandidates.removeLast();
        }
    }

    // * 46. Permutations
    // Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permutationHelper(nums, result, new ArrayList<>(), 0, new HashSet<>());
        return result;
    }

    private void permutationHelper(int[] nums, List<List<Integer>> resultList, List<Integer> currentPath, int count,
                                   Set<Integer> visited) {
        if (count == nums.length) {
            resultList.add(new ArrayList<>(currentPath));
            return;
        }
        if (count > nums.length) {
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited.contains(nums[i])) {
                continue;
            }
            currentPath.add(nums[i]);
            visited.add(nums[i]);
            permutationHelper(nums, resultList, currentPath, count + 1, visited);
            currentPath.removeLast();
            visited.remove(nums[i]);
        }
    }

    // * 90. Subsets II
    // Given an integer array nums that may contain duplicates, return all possible
    // subsets (the power set).
    // The solution set must not contain duplicate subsets. Return the solution in any order.
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDupHelper(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void subsetsWithDupHelper(List<List<Integer>> result, List<Integer> currentPath, int[] nums, int currPointer) {
        result.add(new ArrayList<>(currentPath));
        for (int i = currPointer; i < nums.length; i++) {
            if (i > currPointer && nums[i] == nums[i - 1]) continue; // Skip duplicate numbers
            currentPath.add(nums[i]);
            subsetsWithDupHelper(result, currentPath, nums, i + 1);
            currentPath.removeLast();
        }
    }

    // * 40. Combination Sum II
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        combinationSumTwoHelper(candidates, result, new ArrayList<>(), target, 0);
        return result;
    }

    private void combinationSumTwoHelper(int[] candidates, List<List<Integer>> result, List<Integer> currentPath,
                                         int currentSum, int pointer) {
        if (currentSum == 0) {
            result.add(new ArrayList<>(currentPath));
            return;
        }
        if (currentSum < 0) {
            return;
        }
        for (int i = pointer; i < candidates.length; i++) {
            if (i > pointer && candidates[i] == candidates[i - 1]) continue;
            currentPath.add(candidates[i]);
            combinationSumTwoHelper(candidates, result, currentPath,
                    currentSum - candidates[i], i + 1);
            currentPath.removeLast();
        }
    }

    // * 79. Word search
    // * Time complexity - o(n * m * 4**n)
    public boolean exist(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (wordExistDfs(board, word, row, col, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean wordExistDfs(char[][] board, String word, int numRow, int numCol, int currRow, int currCol,
                                int currCharacter) {
        // * Conditions to check

        // 1. If the current character matches the word length, return true
        if (currCharacter >= word.length()) {
            return true;
        }
        // 2. Is it out of bounds - Row and Columns
        if (currRow < 0 || currCol < 0 || currRow >= numRow || currCol >= numCol) {
            return false;
        }
        // 3. Has it been visited before
        // 4. Is it the same as the current character
        if (board[currRow][currCol] != word.charAt(currCharacter)) {
            return false;
        }

        // * Else proceed to recursively call for the neighbours
        board[currRow][currCol] += 100; // Used to mark the current node as visited, will not visit again
        boolean exists =
                wordExistDfs(board, word, numRow, numCol, currRow + 1, currCol, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow, currCol + 1, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow - 1, currCol, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow, currCol - 1, currCharacter + 1);
        board[currRow][currCol] -= 100;
        return exists;
    }

    // * 131. Palindrome Partitioning
    // * Time complexity: O(2^N) Either include character in partition or start new - Each call will
    // potentially double the number of recursive calls. Each call need to be O(n) as well because we want
    // to see if palindrome or not
    // * Space complexity: O(n) height of the tree
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        partitionHelper(result, new ArrayList<>(), 0, s);
        return result;
    }

    public void partitionHelper(List<List<String>> result, List<String> currentPartition, int startIndex, String s) {
        if (startIndex == s.length()) { // Ending condition is when it reaches the end of the string
            result.add(new ArrayList<>(currentPartition));
            return;
        }

        // iterate down
        for (int end = startIndex; end < s.length(); end++) {
            if (!isPalindrome(s.substring(startIndex, end + 1))) continue;
            currentPartition.add(s.substring(startIndex, end + 1));
            partitionHelper(result, currentPartition, end + 1, s);
            currentPartition.removeLast();
        }
    }

    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // * 17. Letter Combinations of a Phone Number
    // * Time complexity - The number of combinations (n * 4^n)
    // * Space complexity - o(n) Length of the String
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) {
            return result;
        }
        letterCombinationsHelper(result, new StringBuilder(), digits, 0);
        return result;
    }

    public void letterCombinationsHelper(List<String> result, StringBuilder path, String digits,
                                         int currPosition) {
        if (currPosition >= digits.length()) {
            result.add(path.toString());
            return;
        }
        for (char nextCharacter : numberLetterMap.get(digits.charAt(currPosition))) {
            path.append(nextCharacter);
            letterCombinationsHelper(result, path, digits, currPosition + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }

    private final Map<Character, char[]> numberLetterMap = Map.of(
            '2', "abc".toCharArray(),
            '3', "def".toCharArray(),
            '4', "ghi".toCharArray(),
            '5', "jkl".toCharArray(),
            '6', "mno".toCharArray(),
            '7', "pqrs".toCharArray(),
            '8', "tuv".toCharArray(),
            '9', "wxyz".toCharArray()
    );

    // * 698. Partition to K equal Sum Subsets
    // * Key - Finding all possible combinations = backtracking question
    public boolean canPartitionKSubsets(int[] nums, int k) {
        // * Each partition must have a sum of sum(nums) / k
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) return false;
        int target = sum / k;

        Arrays.sort(nums);
        // * General thought process
        // Instead of trying to create all of the bucket at once, we will create it one by one
        // For each bucket and a given value, choose to either include the number / dont include the number (DFS)-height would be N
        // At the end of each "Subset", Repeat the process for the next k subsets but just avoid the indexe
        // that were used in the prev subset

        // 1. Maintain an array to keep track if the index was used
        boolean[] used = new boolean[nums.length];
        return canPartitionSubsetsDFS(0, k, 0, target, used, nums);
    }

    private boolean canPartitionSubsetsDFS(int currIndex, int partitionsLeft, int currSum,
                                           int targetValue, boolean[] used,
                                           int[] nums) {
        // Return case;
        if (partitionsLeft == 0) return true;

        // Means reduce the number of partition by 0, reset the index to 0
        if (currSum == targetValue) {
            return canPartitionSubsetsDFS(0, partitionsLeft - 1, 0, targetValue, used, nums);
        }

        // Iterate through nums
        for (int i = currIndex; i < nums.length; i++) {
            // If the value is used / value + current sum > target sum, continue
            if (used[i] || currSum + nums[i] > targetValue) {
                continue;
            }

            // Else, set it to be used
            used[i] = true;
            // +1 to prevent it from adding back the same value
            if (canPartitionSubsetsDFS(i + 1, partitionsLeft, currSum + nums[i], targetValue, used, nums)) return true;
            used[i] = false;
        }
        return false;
    }

    // * 1415. The k-th Lexicographical String of All Happy Strings of Length n
    // * Time complexity: o( 2^n) - 3 choices for each character
    // * Space complexity: o(2^n) - Size of the generated strings
    char[] happyString = new char[]{'a', 'b', 'c'};

    public String getHappyString(int n, int k) {
        List<String> generatedStrings = new ArrayList<>();
        generateAllPossibleCombinations(n, generatedStrings, k, new StringBuilder());

        if (generatedStrings.size() < k) {
            return "";
        }
        return generatedStrings.get(k - 1);
    }

    public void generateAllPossibleCombinations(int n, List<String> generatedStrings, int k, StringBuilder sb) {
        if (sb.length() == n) {
            generatedStrings.add(sb.toString());
            return;
        }
        if (generatedStrings.size() == k) {
            return;
        }

        for (char chr : happyString) {
            if (sb.isEmpty() || sb.charAt(sb.length() - 1) != chr) {
                sb.append(chr);
                generateAllPossibleCombinations(n, generatedStrings, k, sb);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
