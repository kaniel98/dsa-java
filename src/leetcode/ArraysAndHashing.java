package leetcode;

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

    // * 560. Subarray Sum equals K
    // * Time complexity -  o(n) (Iterate through the array once)
    // * Space complexity - o(n) at most o(n) map size
    public int subarraySum(int[] nums, int k) {
        // Hashmap - Keep track of prefix sum and the number of times it appears
        // Explanation - E.g., Target sum is K
        // 1. Keep track of the total sum and the prefix sum
        // 2. Take total sum - target, check if prefix sum is present in hashmap
        // 3. The number of time prefix sum appears = the number of arrays that can match the target
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

    // * 659. Encode and Decode String
    // * Time complexity - o (n)
    // * Space complexity - o (n)
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length());
            sb.append("#");
            sb.append(str);
        }
        return sb.toString();
    }

    public List<String> decode(String str) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            int j = i;
            while (str.charAt(j) != '#') j++;

            int length = Integer.valueOf(str.substring(i, j));
            i = j + 1 + length;
            result.add(str.substring(j + 1, i));
        }

        return result;
    }

    // * 554. Brick Wall
    // * Time complexity: o(k n)
    // * Space complexity: o(n) - Always the number of possible gaps
    public int leastBricks(List<List<Integer>> wall) {
        // 1. Identify the gaps and count them instead for each "1" tile
        HashMap<Integer, Integer> gaps = new HashMap<>();
        int maxGaps = 0;

        for (List<Integer> row : wall) {
            int count = 0;
            // Note that the wall gap (Which is at the end) will not count;
            for (int i = 0; i < row.size() - 1; i++) {
                count += row.get(i);
                gaps.put(count, gaps.getOrDefault(count, 0) + 1);
                maxGaps = Math.max(gaps.get(count), maxGaps);
            }
        }

        return wall.size() - maxGaps;
    }

    // * 1551. Minimum number of operations
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int minOperations(int n) {
        // 1. Main goal is to get all the numbers to reach the middle number
        int min = 1;
        // 2. Max will always be 2 * (n -1) + 1
        int max = 2 * (n - 1) + 1;
        // This is the target we want to reach
        int target = (min + max) / 2;

        int count = 0;
        for (int i = 0; i < n / 2; i++) {
            // 3. Only increment the first half, because the second half will decrement by the same amount as well
            count += target - (2 * i + 1);
        }

        return count;
    }

    // * 525. Continuous Subarray Sum
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixIndexMap = new HashMap<>();
        int currentSum = 0;
        // This helps to check whether 0 is an actual value or if it the default point
        prefixIndexMap.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            int remainder = currentSum % k;
            // If it hits a remainder, it will just take the original remainder because it cancels each other out
            if (prefixIndexMap.containsKey(remainder)) {
                // Take the current pointer - the prefix index
                // This means the sub array is more than 2;
                if (i - prefixIndexMap.get(remainder) >= 2) {
                    return true;
                }
            }

            // Put the remainder and its index into the array - if it does exist
            if (!prefixIndexMap.containsKey(remainder)) {
                prefixIndexMap.put(remainder, i);
            }
        }

        return false;
    }

    // * 179. Largest Number
    // * Time complexity: o(n log n) - Arrays.sort has a time complexity of n log n
    // * Space complexity: o(n)
    public String largestNumber(int[] nums) {
        // 1. Convert the nums into String
        String[] stringNums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            stringNums[i] = Integer.toString(nums[i]);
        }

        // 2. Arrays.sort to be based on - either A + B or B - A
        // e.g., 1 + 2 = 12 vs 2 + 1 = 21
        Arrays.sort(stringNums, (a, b) -> (b + a).compareTo(a + b));

        // * Heap sort can also be used
//        PriorityQueue<String> maxHeap = new PriorityQueue<>(
//                new Comparator<String>() {
//                    @Override
//                    public int compare(String first, String second) {
//                        return (second + first).compareTo(first + second);
//                    }
//                }
//        );

        // Note to handle the edge case where the biggest number is '0'
        if (stringNums[0].equals("0")) {
            return "0";
        }

        // 3. Pop it and add it to the result
        StringBuilder sb = new StringBuilder();
        for (String str : stringNums) {
            sb.append(str);
        }

        // 4. Return the result
        return sb.toString();
    }

    // * 567. Permutation in String
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        // Put into a character array of 26
        int[] frequency = new int[26];
        // This only works because both string are lower case;
        for (Character chr : s1.toCharArray()) {
            frequency[chr - 'a']++;
        }

        int[] permutation = new int[26];

        // Iterate across s2, to see if there is a permutation of s1
        for (int idx = 0; idx < s2.length(); idx++) {
            // Utilise a window for it
            permutation[s2.charAt(idx) - 'a']++;
            // Only remove once it is more than the window size
            if (idx >= s1.length()) {
                permutation[s2.charAt(idx - s1.length()) - 'a']--;
            }

            if (Arrays.equals(frequency, permutation)) {
                return true;
            }
        }

        return false;
    }

    // * 2531. Make Number of Distinct Characters Equal
    // * Time complexity: o(1) - It will always be 26 ** 3
    // * Space complexity: o(1) - It will always be 2 * 26
    public boolean isItPossible(String word1, String word2) {
        // Put both into sets
        int[] wordOneArray = new int[26];
        int[] wordTwoArray = new int[26];

        for (char chr : word1.toCharArray()) {
            wordOneArray[chr - 'a']++;
        }
        for (char chr : word2.toCharArray()) {
            wordTwoArray[chr - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (wordOneArray[i] == 0 || wordTwoArray[j] == 0) {
                    continue;
                }

                // Decrement both
                wordOneArray[i]--;
                wordTwoArray[j]--;
                // Increment the swap
                wordOneArray[j]++;
                wordTwoArray[i]++;

                // Iterate to check if both the same
                int countOne = 0;
                int countTwo = 0;
                for (int idx = 0; idx < 26; idx++) {
                    if (wordOneArray[idx] > 0) countOne++;
                    if (wordTwoArray[idx] > 0) countTwo++;
                }
                if (countOne == countTwo) {
                    return true;
                }

                // Decrement both
                wordOneArray[j]--;
                wordTwoArray[i]--;
                // Increment the swap
                wordOneArray[i]++;
                wordTwoArray[j]++;
            }
        }

        return false;
    }

    // * 3026. Maximum Good Subarray Sum
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public long maximumSubarraySum(int[] nums, int k) {
        // * Maintain a hashmap of the value & the minimum prefix sum to it
        // * For a given distinct value, keep the minimum sum (Will provide the highest sum)
        Map<Integer, Long> map = new HashMap<>();
        long prefixSum = 0;
        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // Add the current prefix to the map - We will store the minimum prefix sum (Get the largest result)
            if (map.getOrDefault(nums[i], Long.MAX_VALUE) > prefixSum) {
                map.put(nums[i], prefixSum);
            }

            prefixSum += nums[i];
            // Proceed to check if the values are available
            if (map.containsKey(nums[i] + k)) {
                maxSum = Math.max(maxSum, prefixSum - map.get(nums[i] + k));
            }
            if (map.containsKey(nums[i] - k)) {
                maxSum = Math.max(maxSum, prefixSum - map.get(nums[i] - k));
            }
        }

        return maxSum == Long.MIN_VALUE ? 0 : maxSum;
    }

    // * 670. Maximum Swap
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int maximumSwap(int num) {
        // If it is a monotonic decreasing stack, no swap will be done
        String str = Integer.toString(num);

        char[] chrArray = str.toCharArray();
        Node[] maxCharAtEachPoint = new Node[chrArray.length];

        char highestNumber = chrArray[chrArray.length - 1];
        int highestNumberPosition = chrArray.length - 1;

        // Iterate from the back
        for (int i = chrArray.length - 1; i >= 0; i--) {
            // Find the largest number and its corresponding position
            if (chrArray[i] > highestNumber) {
                highestNumber = chrArray[i];
                highestNumberPosition = i;
            }
            maxCharAtEachPoint[i] = new Node(highestNumber, highestNumberPosition);
        }

        // Iterate from the front to find the first number that is smaller than or equal
        for (int i = 0; i < chrArray.length; i++) {
            if (chrArray[i] < maxCharAtEachPoint[i].chr) {
                // Swap the positions and break the current for loop
                Node target = maxCharAtEachPoint[i];
                chrArray[target.position] = chrArray[i];
                chrArray[i] = target.chr;
                break;
            }
        }

        // Return back the string
        return Integer.parseInt(String.valueOf(chrArray));
    }

    class Node {
        Character chr;
        Integer position;

        Node(Character chr, Integer position) {
            this.position = position;
            this.chr = chr;
        }
    }

    // * 1502. Can Make Arithmetic Progression From Sequence
    // * Time complexity: o(n log n) - Sorting
    // * Space complexity: o(n) - Sorting
    public boolean canMakeArithmeticProgression(int[] arr) {
        if (arr.length <= 2) {
            return true;
        }

        // 1. Sort the array
        Arrays.sort(arr);

        // 2. Keep track the difference between each two numbers
        int difference = arr[0] - arr[1];

        // 3. If the difference changes, return false
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] - arr[i] != difference) {
                return false;
            }
        }

        return true;
    }

    // * 1546. Maximum Number of Non-Overlapping Subarrays With Sum Equals Target
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int maxNonOverlapping(int[] nums, int target) {
        Map<Integer, Integer> prefixMap = new HashMap<>();
        prefixMap.put(0, -1);
        int currentSum = 0;
        int numOfArrays = 0;
        int left = 0;
        // Right is set to -1 to include the first element
        int right = -1;

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];

            // At this point in time, what is the max number of non overlapping
            if (prefixMap.containsKey(currentSum - target)) {
                // Left Keeps track of where the prefix is located,
                // Right Keeps track of where the subarray is currently moved to
                // It will only add if left is more than right, afterwards, we will move right to the current point
                left = prefixMap.get(currentSum - target);
                if (right <= left) {
                    numOfArrays++;
                    right = i;
                }
            }

            // Only want to keep track of the most recent prefix
            prefixMap.put(currentSum, i);
        }

        return numOfArrays;
    }

    // * 1233. Remove Sub-Folders from the Filesystem
    // * Time complexity: o(n log n + (n * m)) where n is the number of folders and m is the length of the string
    // * Space complexity: o(n)
    public List<String> removeSubfolders(String[] folder) {
        // 1. Sort the string
        // 2. Do a form of DFS to remove the sub strings
        List<String> result = new ArrayList<>();
        Arrays.sort(folder); // n log n

        // The first folder should always be unique
        result.add(folder[0]);
        String currFolder = folder[0] + "/";

        for (int i = 1; i < folder.length; i++) {
            // Compare the substring, it it matches the currFolder, remove it
            // Else, add it to the result folder and set it to be the current folder
            String nextString = folder[i];
            if (nextString.length() >= currFolder.length() && nextString.startsWith(currFolder)) {
                continue;
            }

            result.add(nextString);
            currFolder = nextString + "/";
        }


        return result;
    }

    // * 2501. Longest Square Streak in an Array
    // * Time complexity: o(n log n) - Sorting
    // * Space complexity: o(n) - Storing the square root
    public int longestSquareStreak(int[] nums) {
        // 1. Sort the array
        // 2. Idea is to count up
        // 3. For each num, check if the sqrt of it is present in the map
        // 4. If it is, increment the count and add it as the count for the num
        // 5. Keep track of the max count
        // 6. If it is not present, add it to the map
        Arrays.sort(nums);

        Map<Integer, Integer> squareMap = new HashMap<>();

        // Keeps track of max
        int max = -1;

        for (int num : nums) {
            int sqrt = (int) Math.sqrt(num);
            if (sqrt * sqrt == num && squareMap.containsKey(sqrt)) {
                // e.g., 2 exists in the map, add it to the count of 4.
                squareMap.put(num, squareMap.get(sqrt) + 1);
                max = Math.max(squareMap.get(num), max);
            } else {
                // Add number if it isnt in the map
                squareMap.put(num, 1);
            }
        }

        return max;
    }

    // * 2491. Divide Players Into Teams of Equal Skill
    // * Time complexity: o(n)
    // * Space complexity: o(1) - Constant extra space
    public long dividePlayers(int[] skill) {
        // [3,2,5,1,3,4]
        // * It must be split into n / 2 groups
        // * Each group must add up to - (Total sum / (n / 2) groups)
        // * From there just take the number * num until mid point is reached and return it
        // * No sorting required

        int numOfGroups = skill.length / 2;
        int[] numCount = new int[1001];
        long sum = 0;
        for (int s : skill) {
            numCount[s]++;
            sum += (long) s;
        }

        if (sum % numOfGroups != 0) {
            return -1;
        }

        long targetSum = sum / numOfGroups;
        long totalSum = 0;
        int curr = 0;
        for (int i = 0; i < skill.length; i++) {
            curr = skill[i];
            if (numCount[curr] == 0) {
                continue;
            }
            int diff = (int) targetSum - curr;
            if (numCount[diff] == 0) {
                return -1;
            }
            numCount[curr]--;
            numCount[diff]--;

            totalSum += curr * diff;
        }

        return totalSum == 0 ? -1 : totalSum;
    }

    // * 2521. Distinct Prime Factors of Product of Array
    // * Time complexity: o (n * sqrt(m))
    // * Space complexity: o(n) - Storing the prime numbers
    public int distinctPrimeFactors(int[] nums) {
        Set<Integer> primeNumbers = new HashSet<>();
        // 1. For each number, get the prime factors of it
        for (int num : nums) {
            // Get prime factor
            for (int i = 2; i <= num; i++) {
                if (num % i == 0) {
                    primeNumbers.add(i);
                    while (num % i == 0) {
                        num /= i;
                    }
                }
            }
        }

        return primeNumbers.size();
    }

    // * 945. Minimum Increment to Make Array Unique
    // * Time complexity: o(n)
    // * Space complexity: o(n) - Not ideal
    public int minIncrementForUnique(int[] nums) {
        // 2. Iterate through, and check if it is duplicate
        // 3. If it is a duplicate, get the highest number of increase that would allow it to be unique
        int[] newNums = new int[1000000];
        int increment = 0;

        for (int num : nums) {
            newNums[num] += 1;
        }
        int lastExecuted = -1;

        for (int i = 0; i < 1000000; i++) {
            // I is the unique number which we have to make 1
            if (newNums[i] > 1) {
                if (lastExecuted < i) {
                    lastExecuted = i + 1;
                }

                while (newNums[i] > 1) {
                    while (lastExecuted < 1000000 && newNums[lastExecuted] > 0) {
                        lastExecuted++;
                    }
                    newNums[lastExecuted]++;
                    newNums[i]--;
                    increment += lastExecuted - i;
                }
            }
        }
        return increment;
    }

    // * 945. Minimum Increment to Make Array Unique - Sorting Approach
    public int minIncrementForUniqueSortingApproach(int[] nums) {
        // 1. Sort the nums
        Arrays.sort(nums);
        int increment = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                // At this point in time, the number previous to this would have already been increased to be more
                // thn current number, so we will need to compensate it by adding the difference
                increment += nums[i - 1] - nums[i] + 1;
                nums[i] = nums[i - 1] + 1;
            }
        }

        return increment;
    }

    // * 3146. Permutation Difference between two strings
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int findPermutationDifference(String s, String t) {
        int[] strArray = new int[26];
        for (int i = 0; i < s.length(); i++) {
            strArray[s.charAt(i) - 'a'] = i;
        }

        int difference = 0;

        for (int i = 0; i < t.length(); i++) {
            difference += Math.abs(i - strArray[t.charAt(i) - 'a']);
        }

        return difference;
    }

    // * 189. Rotate Array
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public void rotate(int[] nums, int k) {
        k %= nums.length; // This is the final number of times we need to rotate

        // Reverse the array once
        reverse(nums, 0, nums.length - 1);

        // Now we will reverse the first k elements, and the remaining elements
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);

    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start++;
            end--;
        }
    }
}
