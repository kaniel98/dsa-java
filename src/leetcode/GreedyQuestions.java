package leetcode;

import java.util.HashMap;
import java.util.Map;

public class GreedyQuestions {
    public static void main(String[] args) {
    }

    // * 55. Jump Game - DP / DFS Approach with Memoization
    // * Time complexity - o (n **2) - Not the best
    // * Space complexity - o(n) - Considering the call stack of the array
    public boolean canJump(int[] nums) {
        return canJumpHelper(nums, 0, new HashMap<>());
    }

    // DFS Approach with Memoization
    public boolean canJumpHelper(int[] nums, int currIndex, Map<Integer, Boolean> cache) {
        if (cache.containsKey(currIndex)) {
            return cache.get(currIndex);
        }
        boolean canReach = false;

        // Base cases
        if (currIndex > nums.length - 1) {
            return false;
        }

        if (currIndex == nums.length - 1) {
            return true;
        }

        // Iterating through the current index value
        for (int i = nums[currIndex]; i > 0; i--) {
            canReach = canJumpHelper(nums, currIndex + i, cache);
            cache.put(currIndex + i, canReach);
            if (canReach) {
                return true;
            }
        }
        return false;
    }

    // * 55. Jump Game - Greedy approach
    // * Time complexity - o (n)
    // * Space complexity - o(1)
    public boolean canJumpVersionTwo(int[] nums) {
        // Work backwards - Can the previous elements from the last element reach the end
        int target = nums.length - 1;

        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= target) {
                // 'i' will become the new target, if you can reach 'i' = can reach the end
                target = i;
            }
        }

        // If 'i' reaches 0, means starting from 0, you can reach the end
        // Check if starting reached back to the start
        return target == 0;
    }

    // * 134. Gas Station
    // * Time complexity - o (n)
    // * Space complexity - o(1)
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 1. Collate the "diff" at each station, makes it easier to process later on
        int gasTotal = 0;
        int costTotal = 0;
        for (int i = 0; i < gas.length; i++) {
            gasTotal += gas[i];
            costTotal += cost[i];
        }

        // 2. Make sure sum of gas >= sum of costs, else it wouldn't be possible
        if (gasTotal < costTotal) return -1;

        int gasCount = 0;
        int index = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            gasCount += diff;

            if (gasCount < 0) {
                // Current index won't work, move to the next one
                index = i + 1;
                gasCount = 0;
            }
        }

        return index;
    }
}
