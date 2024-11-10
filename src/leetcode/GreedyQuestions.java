package leetcode;

import java.util.Arrays;
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

    // * 53. Maximum Subarray
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public int maxSubArray(int[] nums) {
        int currSum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(currSum, maxSum);
        }
        return maxSum;
    }

    // * 2541. Minimum Operations to Make Array Equal II
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public long minOperations(int[] nums1, int[] nums2, int k) {
        // 1. Collate the difference as a separate array
        int[] diff = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            diff[i] = nums1[i] - nums2[i];
        }

        // Base case for if k == 0, there should be any difference
        if (k == 0) {
            for (int d : diff) {
                if (d != 0) {
                    return -1;
                }
            }
            return 0;
        }

        long sum = 0;
        long operations = 0;
        // Goal here: Keep track of the sum (Either to add or decrease, if it is possible, the sum will remain 0)
        // We will also keep track of operations to either increase or decrease the difference by k to 0;
        for (long d : diff) {
            sum += d;
            if (d % k != 0) {
                return -1;
            }
            // Add either the increment or decrement
            operations += Math.abs(d / k);
        }

        // Towards the end, the operations will be reduced by half
        return sum == 0 ? operations / 2 : -1;
    }

    // * 1029. Two City Scheduling
    // * Time complexity: o(n log n)
    // * Space complexity: o(n)
    public int twoCitySchedCost(int[][] costs) {
        // 1. Get the difference between sending to A vs B
        // 2. Based on this difference, we can identify whether it is cheaper or more expensive to send the client to A in this case
        // 3. We will send those which are cheaper to B and the remaining to B
        // 4. The logic applies vice versa if we find which is cheaper to send to A

        int[][] difference = new int[costs.length][2];
        for (int i = 0; i < costs.length; i++) {
            int[] cost = costs[i];
            difference[i] = new int[]{cost[0] - cost[1], i};
        }

        // Proceed to sort in ascending
        Arrays.sort(difference, (a, b) -> a[0] - b[0]);

        // In the first half, it would be cheaper to send these individuals to A
        // In the second half, it would be cheaper to send these individuals to B
        int count = costs.length / 2;
        int totalCost = 0;
        for (int i = 0; i < count; i++) {
            int[] diff = difference[i];
            totalCost += costs[diff[1]][0]; // Send to A for first half;
        }

        for (int i = count; i < costs.length; i++) {
            int[] diff = difference[i];
            totalCost += costs[diff[1]][1]; // Send to A for first half;
        }

        return totalCost;
    }
}
