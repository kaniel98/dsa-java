package leetcode;

import java.util.Arrays;

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
}
