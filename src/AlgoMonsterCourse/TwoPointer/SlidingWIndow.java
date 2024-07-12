package AlgoMonsterCourse.TwoPointer;

import java.util.List;

public class SlidingWIndow {
    public static void main(String[] args) {

    }

    // * Subarray sum fixed
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public static int subarraySumFixed(List<Integer> nums, int k) {
        // Note array cannot be sorted, it is fixed
        int currSum = 0;
        int left = 0;
        for (int i = 0; i < k; i++) {
            currSum += nums.get(i);
        }
        int maxSum = currSum;
        for (int right = k; right < nums.size(); right++) {
            currSum -= nums.get(left);
            currSum += nums.get(right);
            maxSum = Math.max(currSum, maxSum);
            left++;
        }
        return maxSum;
    }
}
