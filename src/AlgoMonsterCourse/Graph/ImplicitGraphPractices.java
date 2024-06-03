package AlgoMonsterCourse.Graph;

import java.util.*;

public class ImplicitGraphPractices {
    public static void main(String[] args) {

    }

    // * 752. Open the lock
    // * Time complexity - o(n) (Number of possible combinations)
    // * Space complexity - o(n) (Storing number of steps)
    public int numSteps(String targetCombo, List<String> trappedCombos) {
        Set<String> trappedComboSet = new HashSet<>(trappedCombos);
        Map<String, Integer> visited = new HashMap<>();
        // of steps needed
        Map<String, String> nextDigit = Map.of(
                "0", "1",
                "1", "2",
                "2", "3",
                "3", "4",
                "4", "5",
                "5", "6",
                "6", "7",
                "7", "8",
                "8", "9",
                "9", "0"
        );
        Map<String, String> prevDigit = Map.of(
                "1", "0",
                "2", "1",
                "3", "2",
                "4", "3",
                "5", "4",
                "6", "5",
                "7", "6",
                "8", "7",
                "9", "8",
                "0", "9"
        );

        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.offer("0000"); // Starting combination
        visited.put("0000", 0);
        while (!queue.isEmpty()) {
            String currentCombination = queue.poll();
            List<String> neighbours = this.getNeighboursNumSteps(currentCombination, nextDigit, prevDigit);
            for (String neighbour : neighbours) {
                if (visited.containsKey(neighbour) || trappedComboSet.contains(neighbour)) {
                    continue;
                }
                queue.offer(neighbour);
                visited.put(neighbour, visited.get(currentCombination) + 1); // Take the number of steps to get to
                // the current combination and + 1;
                if (neighbour.equals(targetCombo)) {
                    return visited.get(neighbour);
                }
            }
        }

        // * If no combinations is found, return -1
        return -1;
    }

    private List<String> getNeighboursNumSteps(String currentCombination, Map<String, String> nextDigitMap,
                                               Map<String, String> prevDigitMap) {

        // * Two combinations are adjacent if they different by one digit (Any of the one digit)
        // * Each node = one combination, each edge = difference of one digit
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // Get next digit
            neighbours.add(
                    currentCombination.substring(0, i).concat(nextDigitMap.get(String.valueOf(currentCombination.charAt(i)))).concat(currentCombination.substring(i + 1))
            );
            // Get prev digit
            neighbours.add(
                    currentCombination.substring(0, i).concat(prevDigitMap.get(String.valueOf(currentCombination.charAt(i)))).concat(currentCombination.substring(i + 1))
            );
        }
        return neighbours;
    }
}
