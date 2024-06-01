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
        board[currRow][currCol] += 100;
        boolean exists =
                wordExistDfs(board, word, numRow, numCol, currRow + 1, currCol, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow, currCol + 1, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow - 1, currCol, currCharacter + 1) ||
                        wordExistDfs(board, word, numRow, numCol, currRow, currCol - 1, currCharacter + 1);
        board[currRow][currCol] -= 100;
        return exists;
    }
}
