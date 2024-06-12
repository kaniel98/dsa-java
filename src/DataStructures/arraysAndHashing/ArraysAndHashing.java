package DataStructures.arraysAndHashing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // * 554. Brick Wall
    // * Time complexity: o(n) - Need to go through each element
    // * Space Complexity: o(n) - Maximum size of the row
    public int leastBricks(List<List<Integer>> wall) {
        // Initialize a hashmap to keep track the number of gaps at every increment (Possible gap)
        // Go through each of the elements, if it is more than or less than the element, we will add it to the
        // hashmap (Means a gap is found)
        // Towards the end, just return the gap with the highest count
        Map<Integer, Integer> numberOfGapsMap = new HashMap<>();
        int maxGaps = 0;
        for (List<Integer> row : wall) {
            int position = 0;
            for (int i = 0; i < row.size() - 1; i++) { // The last brick is always skipped because the end of the wall
                // doesn't count
                position += row.get(i); // Location of the first gap
                numberOfGapsMap.put(position, numberOfGapsMap.getOrDefault(position, 0) + 1);
                maxGaps = Math.max(maxGaps, numberOfGapsMap.get(position));
                // Compare and see which has the most gaps now
            }
        }
        return wall.size() - maxGaps;
    }
}
