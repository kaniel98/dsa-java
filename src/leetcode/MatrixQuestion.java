package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MatrixQuestion {
    public static void main(String[] args) {

    }

    // * 2022. Convert 1D Array Into 2D Array
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public int[][] construct2DArray(int[] original, int m, int n) {
        // 1. Check if the length of original is able to fit the matrix first
        if (original.length != (m * n)) {
            return new int[0][];
        }

        // Proceed to create the new matrix and return it
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            result[i] = Arrays.copyOfRange(original, i * n, i * n + n);
        }

        return result;
    }

    // 1072. Flip Columns For Maximum Number of Equal Rows
    // Time complexity: o(n * m)
    // Space complexity: o(n)
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        // Find the number of flips required to get the max number of rows with the same values
        // * It is too expensive to flip every column and check
        // * Either find rows of the same value or invered values of it
        // * Inversed row will count because consider the following
        int max = 0;
        Map<String, Integer> count = new HashMap<>();

        for (int[] row : matrix) {
            String rowString = Arrays.toString(row);

            if (row[0] == 0) { // Store only the rows with 1
                rowString = getInversedValue(row);
            }

            count.put(rowString, count.getOrDefault(rowString, 0) + 1);
            max = Math.max(max, count.get(rowString));
        }

        return max;
    }

    public String getInversedValue(int[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1) {
                row[i] = 0;
            } else {
                row[i] = 1;
            }
        }
        return Arrays.toString(row);
    }
}
