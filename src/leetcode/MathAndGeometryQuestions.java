package leetcode;

import java.util.HashSet;
import java.util.Set;

public class MathAndGeometryQuestions {
    public static void main(String[] args) {

    }

    /*
     *  73. Set Matrix Zeroes
     */


    // * 73. Set Matrix Zeroes
    // * Time complexity: o (n * m)
    // * Space complexity: o (n + m)
    public void setZeroes(int[][] matrix) {
        // Maintain two arrays - Row and Column
        // Keep track of which row / column is set to zero
        // At the end, do a for loop and set it to zero
        Set<Integer> affectedRows = new HashSet<>();
        Set<Integer> affectedColumns = new HashSet<>();

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    affectedRows.add(row);
                    affectedColumns.add(col);
                }
            }
        }

        // Loop through and set it to zero accordingly
        for (int row = 0; row < matrix.length; row++) {
            // Set the entire row to zero and skip
            if (affectedRows.contains(row)) {
                for (int col = 0; col < matrix[0].length; col++) {
                    matrix[row][col] = 0;
                }
                continue;
            }

            // Else, set 0 for the columns
            for (int col : affectedColumns) {
                matrix[row][col] = 0;
            }
        }
    }
}
