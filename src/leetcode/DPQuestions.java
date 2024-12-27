package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DPQuestions {
    /*
     * Questions solved:
     * 62. Unique Paths
     */

    // * 62. Unique Paths - DFS Approach
    // * Time complexity - o(m * n)
    // * Space complexity - o(m + n);
    Map<String, Integer> memoMap = new HashMap<>();

    public int uniquePaths(int m, int n) {
        // DFS - Find all possible paths to the end
        return uniquePathHelper(0, 0, m, n);
    }

    public int uniquePathHelper(Integer currRow, Integer currCol, Integer maxRow, Integer maxCol) {
        if (currRow == maxRow - 1 && currCol == maxCol - 1) {
            return 1;
        }

        String currCoord = currRow + ":" + currCol;
        if (memoMap.containsKey(currCoord)) {
            return memoMap.get(currCoord);
        }

        Integer currPaths = 0;
        // Go down either down or right + make sure it doesnt hit the boundary
        // Moving down
        if (currRow + 1 < maxRow) {
            currPaths += uniquePathHelper(currRow + 1, currCol, maxRow, maxCol);
        }
        // Moving right
        if (currCol + 1 < maxCol) {
            currPaths += uniquePathHelper(currRow, currCol + 1, maxRow, maxCol);
        }

        memoMap.put(currCoord, currPaths);
        return currPaths;
    }

    // * 62. Unique Paths - DP Approach
    // * Time complexity - o(m * n)
    // * Space complexity - o(m * n);
    public int uniquePathsDP(int m, int n) {
        int[][] dp = new int[m][n];

        // Fill out last row
        for (int j = 0; j < n; j++) {
            dp[m - 1][j] = 1;
        }

        // Fill out last column
        for (int i = 0; i < m; i++) {
            dp[i][n - 1] = 1;
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = dp[i][j + 1] + dp[i + 1][j];
            }
        }
        return dp[0][0];
    }


    // * 322. Coin Change
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int coinChange(int[] coins, int amount) {
        // Base cases
        if (amount == 0 || coins.length == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        // Iterate through from dp[1] all the way to dp[11]
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) { // We will not consider combinations below 0;
                    // + 1 because it needs to include the current coin, and dp[i - coin] is to get the minimum for that remainder
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }


        return dp[amount] != amount + 1 ? dp[amount] : -1;
    }

    // * 746. Min Cost Climbing Stairs
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int minCostClimbingStairs(int[] cost) {
        // Basically need to find the lowest cost until the 2nd last step
        int[] result = new int[cost.length];
        result[0] = cost[0];
        result[1] = cost[1];

        for (int i = 2; i < cost.length; i++) {
            int min = Math.min(cost[i] + result[i - 1], cost[i] + result[i - 2]);
            result[i] = min;
        }

        return Math.min(result[cost.length - 2], result[cost.length - 1]);
    }

    // * 198. House Robber
    // * Time complexity: o(n)
    // * Space complexity: o(n) - Can be reduced to o(1) by using 3 variables
    public int rob(int[] nums) {
        int[] result = new int[nums.length];

        // Base case for the first 3 numbers
        if (nums.length == 1) {
            return nums[0];
        }

        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        if (nums.length == 3) {
            return Math.max(nums[1], nums[2] + nums[0]);
        }

        // Populate the first three values;
        result[0] = nums[0];
        result[1] = nums[1];
        result[2] = nums[0] + nums[2];

        for (int i = 3; i < nums.length; i++) {
            // The max value for this would either be curr + n - 2 or n - 3;
            result[i] = Math.max(nums[i] + result[i - 2], nums[i] + result[i - 3]);
        }

        // Towards the end the max would either be the last element or the 2nd last
        // System.out.println(Arrays.toString(result));
        return Math.max(result[result.length - 1], result[result.length - 2]);
    }

    // * 198 House Robber - Three variable approach
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int robDP2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int dp0 = 0, dp1 = 0, curr;

        for (int i = 0; i < nums.length; i++) {
            curr = Math.max(dp0 + nums[i], dp1);
            dp0 = dp1;
            dp1 = curr;
        }
        return Math.max(dp0, dp1);
    }

    // * 213. House Robber II
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int robII(int[] nums) {
        // Since index 0 and index (n -1) are adjacent to each other, means they cannot be robbed consecutively
        // If we exclude either index 0 or index (n -1), it would be the same as house robber I
        // Execute the helper method on both first and 2nd
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(robHelper(Arrays.copyOfRange(nums, 1, nums.length)), robHelper(Arrays.copyOfRange(nums, 0, nums.length - 1)));
    }

    public int robHelper(int[] nums) {
        int[] result = new int[nums.length];
        System.out.println(Arrays.toString(nums));

        // Base case for first three numbers;
        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        if (nums.length == 3) {
            return Math.max(nums[1], nums[2] + nums[0]);
        }

        // Iterate through the array
        result[0] = nums[0];
        result[1] = nums[1];
        result[2] = nums[0] + nums[2];

        for (int i = 3; i < nums.length; i++) {
            result[i] = Math.max(nums[i] + result[i - 2], nums[i] + result[i - 3]);
        }

        return Math.max(result[result.length - 1], result[result.length - 2]);
    }

    // * 416. Partition Equal Subset Sum
    // * Time complexity: o(n ** 2) - Due to the memoization, we are checking against all possible combinations
    // * Space complexity: o(n ** 2) - Due to the memoization, we are storing all possible combinations
    boolean canSplit = false;
    Boolean[][] mem;

    public boolean canPartition(int[] nums) {
        // Target sum
        Arrays.sort(nums);

        int total = Arrays.stream(nums).sum();
        if (total % 2 != 0) {
            return false;
        }

        int target = total / 2;
        mem = new Boolean[nums.length + 1][target + 1];


        // Goal now is to find the ints that can meet that num
        return canPartitionHelper(nums, 0, 0, target);
    }

    public boolean canPartitionHelper(int[] nums, int currPointer, int sum, int target) {
        if (sum == target) {
            return true;
        }

        if (sum > target || currPointer >= nums.length) {
            return false;
        }

        // Check if this position and sum was found
        if (mem[currPointer][sum] != null) {
            return mem[currPointer][sum];
        }

        // Decision to either include or exclude the current pointer;
        boolean possible = canPartitionHelper(nums, currPointer + 1, sum + nums[currPointer], target) || canPartitionHelper(nums, currPointer + 1, sum, target);
        mem[currPointer][sum] = possible;
        return possible;
    }

    // * 1014. Best Sightseeing Pair
    // * Time complexity: o(n)
    // * Space complexity: o(1)
    public int maxScoreSightseeingPair(int[] values) {
        /**
         * The goal is to find the maximum value of A[i] + A[j] + i - j
         *  - Minimize the distance between i and j
         *  - Maximize the value of A[i] + A[j]
         *  - Maintain a pointer of the current max value which is what is the max value - distance from the current point
         *  - Compare the current max value  + the current value - 1 with the max value
         */

        int currMax = values[0];
        int max = Integer.MIN_VALUE;

        for (int i = 1; i < values.length; i++) {
            int currValue = values[i];

            // Compare the current combination with the max
            max = Math.max(max, currMax + currValue - 1);
            currMax = Math.max(currValue, currMax - 1);
        }

        return max;
    }
}
