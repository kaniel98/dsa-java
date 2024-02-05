public class Main {
    public static void main(String[] args) {

    }

    public int removeDuplicates(int[] nums) {
        // Initialize two pointers - Left and Right
        // Left will be pointing to the start and make sure that there is only one of every element
        // Right will be moving forward, to make there that there is only one of every element at most.
        // Edge case
        if (nums.length <= 1) {
            return nums.length;
        }
        // Initialize the two pointers
        int left = 0;
        int right = 0;
        // Loop through the nums
        while (right < nums.length) {
            int count = 0;
            // Loop and check how many duplicates
            while (right + 1 < nums.length && nums[right] == nums[right + 1]) {
                right++;
                count++;
            }
            // Proceed to add one time only
            nums[left] = nums[right];
            left++;
            // At the end, make sure that right will move to the start of the next sequence
            right++;
        }

        return left;
    }
}