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

    // * 127. Word Ladder
    public int wordLadder(String begin, String end, List<String> wordList) {
        // 1. Convert the wordList into a hashset so that we can search through it easily
        Set<String> unvisitedWord = new HashSet<>(wordList);

        if (Objects.equals(begin, end)) {
            return 0;
        }
        int currentLevel = 0;
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.offer(begin);
        unvisitedWord.remove(begin);
        // 3. For each neighbour, do a normal BFS - Meaning keep track of visited etc.
        while (!queue.isEmpty()) {
            int n = queue.size();
            currentLevel++;
            for (int i = 0; i < n; i++) {
                String word = queue.poll();
                for (String nextWord : wordLadderGetNeighbours(word, unvisitedWord)) {
                    if (nextWord.equals(end)) return currentLevel;
                    queue.add(nextWord);
                }
            }
        }
        return -1;
    }

    // 2. Based on this, create a helper function which can help to get the "Neighbouring" words
    public List<String> wordLadderGetNeighbours(String currentWord, Set<String> unvisitedWords) {
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < currentWord.length(); i++) {
            for (char c : ALPHABETS) {
                String word = currentWord.substring(0, i) +
                        c +
                        currentWord.substring(i + 1);
                if (unvisitedWords.contains(word)) {
                    neighbours.add(word);
                    unvisitedWords.remove(word);
                }
            }
        }
        return neighbours;
    }

    private static final char[] ALPHABETS = new char[26];

    static {
        // ascii representation of english alphabets a - z are numbers 97 - 122
        for (int i = 0; i < 26; i++) {
            ALPHABETS[i] = (char) (i + 'a');
        }
    }
}
