import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.generate(5));
    }

    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>(List.of(1)));


        for (int i = 0; i < numRows - 1; i++) {
            // Append 0 to the start and the end of the array
            List<Integer> temp = new ArrayList<>(result.get(i));
            temp.add(0, 0);
            temp.add(0);
            // Initiate for the next one
            List<Integer> nextRow = new ArrayList<>();
            for (int inner = 0; inner < temp.size() - 1; inner++) {
                nextRow.add(temp.get(inner) + temp.get(inner + 1));
            }
            result.add(nextRow);
        }
        return result;
    }
}
