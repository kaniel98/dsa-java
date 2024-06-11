package DataStructures.arraysAndHashing;

public class ArraysAndHashing {
    public static void main(String[] args) {

    }

    // * Sort colors
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    // Adopts a quick sort approach, can also use bucket sort but it needs two passes instead of one pass (Quick sort)
    public void sortColors(int[] nums) {
        // Adopt two pointer approach
        int left = 0;
        int mid = 0; // Used to indicate where mid is at
        int right = nums.length - 1;

        while (mid < right) {
            if (nums[mid] == 1) {
                mid++;
            } else if (nums[mid] == 0) { // Swap it with the current position of left
                swapColors(nums, left, mid);
                left++; // Left and mid-pointer needs to increase to prevent it from going into loop
                mid++;
            } else if (nums[mid] == 2) {
                swapColors(nums, mid, right);
                // Only right needs to increase
                // Because if right was e.g., 0, it will be swapped to mid position
                // If we increase mid as well, the original 0 will not be swapped back to the start
                right--;

            }
        }
    }

    private void swapColors(int[] nums, int start, int end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }

}
