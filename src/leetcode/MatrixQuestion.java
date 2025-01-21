package leetcode;

import java.util.*;

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

    // * 1072. Flip Columns For Maximum Number of Equal Rows
    // * Time complexity: o(n * m)
    // * Space complexity: o(n)
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

    // * 1975. Maximum Matrix Sum
    // * Time complexity: o(n * m)
    // * Space complexity: o(n * m)
    public long maxMatrixSum(int[][] matrix) {
        /* You can flip any two adjacent cells two times as many times you like
         * Goal is to get the maximum sum
         * If we draw it out, we will realise that for even numbers of negatives, it can always be flipped to all be positive
         * For odd numbers, only one will remain as unflipped / negative.
         */
        List<Integer> negativeNumbers = new ArrayList<>();
        long sum = 0L;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int idx = 0; idx < matrix.length; idx++) {
                int num = row[idx];
                if (num < 0) {
                    negativeNumbers.add(num);
                } else {
                    sum += num;
                }
                if (num >= 0) {
                    min = Math.min(min, num);
                }
            }
        }

        // Proceed to handle the negative numbers
        if (negativeNumbers.size() % 2 == 0) {
            for (int num : negativeNumbers) {
                sum += Math.abs(num);
            }
        } else {
            // Sort and add all numbers except the largest one
            Collections.sort(negativeNumbers);
            for (int i = 0; i < negativeNumbers.size() - 1; i++) {
                sum += Math.abs(negativeNumbers.get(i));
            }

            // Comparison for the smallest number and the minimum positive number
            int largestNeg = negativeNumbers.get(negativeNumbers.size() - 1);
            if (Math.abs(largestNeg) >= min) {
                sum -= 2L * min;
                sum += Math.abs(largestNeg);
            } else {
                sum += largestNeg;
            }
        }

        return sum;
    }

    // * 2661. First Completely Painted Row or Column
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        // Basically need to keep track if which row / column will be filled up first
        int targetRowCount = mat.length;
        int targetColCount = mat[0].length;

        // Create a mapping of each point instead, so that we know what row/col to mark
        Map<Integer, Point> pointMap = new HashMap<>();
        for (int row = 0; row < mat.length; row++) {
            for (int col = 0; col < mat[row].length; col++) {
                pointMap.put(mat[row][col], new Point(row, col));
            }
        }
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Point point = pointMap.get(arr[i]);

            // Add the point to row and col
            String rowKey = "row" + point.row;
            String colKey = "col" + point.col;
            countMap.put(rowKey, countMap.getOrDefault(rowKey, 0) + 1);
            countMap.put(colKey, countMap.getOrDefault(colKey, 0) + 1);

            if (countMap.get(rowKey) == targetColCount || countMap.get(colKey) == targetRowCount) {
                return i;
            }
        }

        return -1;
    }

    class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
