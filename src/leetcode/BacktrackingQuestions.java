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

    // * 1593. Split a String Into the Max Number of Unique Substrings
    // * Time complexity: o(n * 2^n) - 2^n for the number of possible substrings and n for the substring
    // * Space complexity: o(n) - The size of the formed strings + the recursion stack
    int max = 0;

    public int maxUniqueSplit(String s) {
        // Execute recursion, keep a map to find the max at each length before returning it
        maxUniqueSplitHelper(s, new HashSet<>(), 0);
        return max;
    }

    public void maxUniqueSplitHelper(String s, Set<String> formedStrings, int currPoint) {
        // Base case
        if (currPoint >= s.length()) {
            max = Math.max(max, formedStrings.size());
        }

        int max = 0;

        // Execute from the current point
        for (int i = currPoint + 1; i <= s.length(); i++) {
            String currSubString = s.substring(currPoint, i);

            // If a current substring is found, continue
            if (formedStrings.contains(currSubString)) {
                continue;
            }

            // Proceed to add the substring to hashset and continue the iteration
            formedStrings.add(currSubString);
            maxUniqueSplitHelper(s, formedStrings, i);
            formedStrings.remove(currSubString);
        }
    }

    // * 263. Ugly Number
    // * Time complexity: o(log n) - We are dividing the number by 2, 3, 5
    // * Space complexity: o(1) - Constant space
    public boolean isUgly(int n) {
        // Base case of 0
        if (n == 0) return false;

        if (n > 0 && n <= 3) return true;

        if (n % 2 == 0) return isUgly(n / 2);
        if (n % 3 == 0) return isUgly(n / 3);
        if (n % 5 == 0) return isUgly(n / 5);

        return false;
    }

    // * 300. Longest Increasing Subsequence - Possible to do with just DP to reduce memory
    // * Time complexity: o(n^2) - For each element, we are iterating through the array
    // * Space complexity: o(n) - The size of the cache
    int maxLength = 0;

    public int lengthOfLIS(int[] nums) {
        lengthOfLISHelper(nums, 0, new HashMap<>());
        return maxLength;
    }

    public int lengthOfLISHelper(int[] nums, int currPointer, Map<Integer, Integer> cache) {
        // If a max subsequence is already found for the curr pointer, return it;
        if (cache.containsKey(currPointer)) {
            return cache.get(currPointer);
        }
        int currMax = 1;

        for (int i = currPointer + 1; i < nums.length; i++) {
            if (nums[currPointer] >= nums[i]) {
                Math.max(currMax, lengthOfLISHelper(nums, i, cache));
                continue; // If smaller or less, skip
            }
            currMax = Math.max(currMax, 1 + lengthOfLISHelper(nums, i, cache));
        }
        cache.put(currPointer, currMax);
        maxLength = Math.max(maxLength, currMax);
        return currMax;
    }

    // * 2466. Count Ways To Build Good Strings
    // * Time complexity: o(n) - The number of possible strings
    // * Space complexity: o(n) - The size of the cache
    HashMap<Integer, Long> countMap;
    int mod = 1_000_000_007;

    public int countGoodStrings(int low, int high, int zero, int one) {
        // DFS Question with Memoisation (Top down approach in DP)
        countMap = new HashMap<>();
        return (int) countGoodStringsDfs(low, high, zero, one, 0);
    }

    public long countGoodStringsDfs(int low, int high, int zero, int one, int length) {
        // If the count exceeds more than high, it is no longer valid
        if (length > high) {
            return 0;
        }
        if (countMap.containsKey(length)) {
            return countMap.get(length); // Memoisation (For this current length, has the work been done?)
        }

        long res = 0;
        // Check if the current string is valid
        if (length >= low) {
            res++;
        }
        // Proceed to include results from permutations further down the tree
        res += countGoodStringsDfs(low, high, zero, one, length + zero) + countGoodStringsDfs(low, high, zero, one, length + one);
        // Store the current result in the map (It doesnt exist if it gets to here)
        countMap.put(length, res);
        return res % mod;
    }

    // * 1718. Construct the Lexicographically Largest Valid Sequence
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int[] largest;

    public int[] constructDistancedSequence(int n) {
        // Array of n size to keep track of the number of n used
        largest = new int[n * 2 - 1];
        // Go through each of the possible routes

        constructDistancedSequenceDfs(n + 1, new HashSet<>(), new int[n * 2 - 1], 0);
        return largest;
    }

    public boolean constructDistancedSequenceDfs(int n, Set<Integer> visited, int[] currentList, int currIdx) {
        if (currIdx >= currentList.length) {
            // Some comparison logic
            largest = currentList.clone();
            return true;
        }

        if (currentList[currIdx] != 0) {
            return constructDistancedSequenceDfs(n, visited, currentList, currIdx + 1);
        }

        for (int i = n - 1; i > 0; i--) {
            if (visited.contains(i)) {
                continue;
            }

            visited.add(i);
            currentList[currIdx] = i;

            if (i == 1) {
                if (constructDistancedSequenceDfs(n, visited, currentList, currIdx + 1)) {
                    return true;
                }
            }

            // Check if can put at the current idx & not filled
            if (currIdx + i < currentList.length && currentList[currIdx + i] == 0) {
                currentList[currIdx + i] = i;
                if (constructDistancedSequenceDfs(n, visited, currentList, currIdx + 1)) {
                    return true;
                }
                currentList[currIdx + i] = 0;
            }

            visited.remove(i);
            currentList[currIdx] = 0;
        }

        return false;
    }
}
