package leetcode;

public class BitQuestion {

    // * 136. Single Number
    // * Time complexity: O(n)
    // * Space complexity: O(1)
    public int singleNumber(int[] nums) {
        int res = nums[0];
        // * Goes by the concept of XOR, Duplicate numbers will cancel each other out
        // * Only the number that appears only once will remain
        for (int i = 1; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }
}
