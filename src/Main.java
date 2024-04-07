import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(Arrays.toString(main.productExceptSelf(new int[]{1, 2, 3, 4})));
    }

    public int[] productExceptSelf(int[] nums) {
        // Get the length of the array
        int len = nums.length;

        // From here, calculate the product of each number starting from the left and store it into an array
        int[] result = new int[len];
        // The start and the end should just be 1, not included
        int left = 1;
        for (int i = 0; i < len; i++) {
            // Makes sure that it starts from the second index
            if (i > 0) {
                left = left * nums[i - 1];
            }
            result[i] = left;
        }

        // Starting from the right, multiple each number in the left with this the element from the end of the array
        int right = 1;
        for (int i = len - 1; i >= 0; i--) {
            // Makes sure it starts from the second last index
            if (i < len - 1) {
                right = right * nums[i + 1];
            }
            result[i] *= right;
        }

        return result;
    }

}