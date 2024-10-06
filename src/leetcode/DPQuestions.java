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
}
