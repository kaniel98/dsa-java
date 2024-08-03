package Leetcode;

import java.util.*;

public class ArraysAndHashing {
    public static void main(String[] args) {
    }

    // * 217 - Contains duplicate
    // * Time Complexity - o(n), Space Complexity - o(n)
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> present = new HashSet<>();
        for (int num : nums) {
            if (present.contains(num)) {
                return true;
            }
            present.add(num);
        }
        return false;
    }

    // * 242 - Valid anagram
    // * Time complexity - o(s + t), Space complexity - O(1)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] store = new int[26];
        for (int i = 0; i < s.length(); i++) {
            store[s.charAt(i) - 'a']++;
            store[t.charAt(i) - 'a']--;
        }
        for (int num : store) {
            if (num != 0) return false;
        }
        return true;
    }

    // * 49 - Group anagrams
    // * Time complexity - o(N * M), Space complexity - o (M)
    public List<List<String>> groupAnagrams(List<String> stringList) {
        Map<String, List<String>> map = new HashMap<>();
        for (String string : stringList) {
            int[] temp = new int[26];
            // Convert each string into a char array - Sorted accordingly with char count
            for (char chr : string.toCharArray()) {
                temp[chr - 'a']++;
            }
            // Create a string to concatenate these characters together
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                key.append("#");
                key.append(temp[i]);
            }
            // If the map does not have this new string as a key, create a new array list for it
            if (!map.containsKey(key.toString())) {
                map.put(key.toString(), new ArrayList<>());
            }
            map.get(key.toString()).add(string);
        }
        // Return the result
        return map.values().stream().toList();
    }

    // * 347 - Top K Frequent elements
    // * Time complexity - o(n), Space complexity - o(n)
    public int[] topKFrequentElements(int[] nums, int k) {
        // 1. Convert into a hashmap - num : number of times it appear
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        // 2. Convert it into an array - Bucket list - Count = index
        List<Integer>[] tempStore = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int count = entry.getValue();
            if (tempStore[count] == null) {
                tempStore[count] = new ArrayList<>();
            }
            tempStore[count].add(entry.getKey());
        }
        // 3. Iterate from the back and add it to result
        int[] result = new int[k];
        int index = 0;
        for (int i = tempStore.length - 1; i >= 0 && index < k; i--) {
            if (tempStore[i] != null) {
                for (int num : tempStore[i]) {
                    result[index++] = num;
                }
            }
        }
        return result;
    }

    // * 36. Valid Sudoku
    // * Time complexity o(n**2) where n = number of numbers
    // * Space complexity o(n + m) where n is number of rows and m is number of cols
    public boolean isValidSudoku(char[][] board) {
        // Hashset for Column
        // Hashset for Row
        HashSet<Character> colSet = null;
        HashSet<Character> rowSet = null;

        for (int i = 0; i < 9; i++) {
            colSet = new HashSet<>();
            rowSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                char rowChar = board[i][j]; // Iterate across the current row
                char colChar = board[j][i]; // Iterate down for the column;

                // For row
                if (rowChar != '.') {
                    if (rowSet.contains(rowChar)) {
                        return false;
                    }
                    rowSet.add(rowChar);
                }

                // For col
                if (colChar != '.') {
                    if (colSet.contains(colChar)) {
                        return false;
                    }
                    colSet.add(colChar);
                }
            }
        }

        // Checking for columns
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                if (!checkBlock(i, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkBlock(int indexRow, int indexCol, char[][] board) {
        Set<Character> blockSet = new HashSet<>();
        int endRow = indexRow + 3; // Stop checking at this given row
        int endCol = indexCol + 3; // Stop checking at this given col
        for (int i = indexRow; i < endRow; i++) {
            for (int j = indexCol; j < endCol; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (blockSet.contains(board[i][j])) {
                    return false;
                }
                blockSet.add(board[i][j]);
            }
        }
        return true;
    }

    // * 128 Longest consecutive sequence
    // * Space complexity - o(n)
    // * Time complexity - o(2n)
    public int longestConsecutive(int[] nums) {
        // Put each num in hashset
        HashSet<Integer> store = new HashSet<>();
        for (int num : nums) {
            store.add(num);
        }
        int longestConsecutive = 0;

        // Iterate through the nums
        for (int num : nums) {
            // Check if there is a num before the current one (Means it will not be the longest)
            if (store.contains(num - 1)) {
                continue; // Means this will not be the longest sequence
            }
            int currentLongest = 1;
            while (store.contains(num + 1)) {
                num++;
                currentLongest++;
            }
            longestConsecutive = Math.max(currentLongest, longestConsecutive); // Optional depending on the question
        }
        return longestConsecutive;
    }

    // * 238 Product of Array except self
    // * Time complexity - o (2n)
    // * Space complexity - o(1) (No extra memory needed)
    public int[] productExceptSelf(int[] nums) {
        int left = 1;
        int right = 1;
        int len = nums.length;
        int[] result = new int[len];
        // Left to right
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                left = nums[i - 1] * left;
            }
            result[i] = left;
        }
        // Right to left
        for (int i = len - 1; i >= 0; i--) {
            if (i < len - 1) {
                right = nums[i + 1] * right;
            }
            result[i] *= right;
        }
        return result;
    }

    // * 560. Sub-array Sum equals K
    // * Time complexity -  o(n) (Iterate through the array once)
    // * Space complexity - o(n) at most o(n) map size
    public int subarraySum(int[] nums, int k) {
        // Hashmap - Keep track of prefix sum and the number of times it appears
        // Explanation - E.g., Target sum is K
        // 1. Keep track of the total sum and the prefix sum
        // 2. Take total sum - target, check if prefix sum is present in hashmap
        // 3. The number of time pefix sum appears = the number of arrays that can match the target
        // with the current end point

        Map<Integer, Integer> prefixMap = new HashMap<>();
        int res = 0; // Keeps track of the number of combinations
        int currentSum = 0; // Keeps track of the current sum
        prefixMap.put(0, 1); // Marks the start of the iteration
        for (int num : nums) {
            currentSum += num;
            // If it contains, add it to res
            if (prefixMap.containsKey(currentSum - k)) {
                res += prefixMap.get(currentSum - k);
            }
            // Add back to prefix sum again
            prefixMap.put(currentSum, prefixMap.getOrDefault(currentSum, 0) + 1);
        }
        return res;
    }

}
