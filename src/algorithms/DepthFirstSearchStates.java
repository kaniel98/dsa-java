package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Practices involving Depth First Search with States (Backtracking)
public class DepthFirstSearchStates {
//    function dfs(start_index, path):
//            if is_leaf(start_index):
//    report(path)
//    return
//            for edge in get_edges(start_index):
//            path.add(edge)
//    dfs(start_index + 1, path)
//    path.pop()

    public static List<String> ternaryTreePaths(Node<Integer> root) {
        // WRITE YOUR BRILLIANT CODE HERE
        return List.of();
    }

    // Get all possible paths for a ternary tree
    // Time complexity - o(n)
    // Space complexity - o(h) number of function calls + o(n**2) number of arrays
    public static void dfsAllPossiblePaths(Node<Integer> root, ArrayList<String> path, ArrayList<String> res) {
        // Case 1: When the node has no more children
        if (root.children.size() == 0) {
            path.add(Integer.toString(root.val)); // Add the current node
            res.add(String.join("->", path)); // Add the current path to result
            return; // Break the current loop
        }

        // Case 2: Proceed to call DFS on each node
        for (Node<Integer> child : root.children) {
            // Create a new copy for each path of the tree
            ArrayList<String> newPath = new ArrayList<>(path);
            // Proceed to add the next current node into the path
            newPath.add(Integer.toString(root.val));
            dfsAllPossiblePaths(child, newPath, res);
        }
    }

    // Inefficient to continuously create new array - Use a stack instead
    public static void dfsAllPossiblePathWithStack(Node<Integer> root, ArrayList<String> path, ArrayList<String> res) {
        // Case 1: When the root has no more children
        if (root.children.size() == 0) {
            path.add(Integer.toString(root.val)); // Add the current node
            res.add(String.join("->", path)); // Add the current path to result
            path.remove(path.size() - 1);
            return;
        }

        for (Node<Integer> child : root.children) {
            if (child != null) {
                path.add(Integer.toString(root.val)); // Add the current root to the path
                dfsAllPossiblePathWithStack(child, path, res);
                path.remove(path.size() - 1); // Remove the current one since it is no longer required
            }
        }
    }

    // * ########################################################################

    public static List<String> ternaryTreePath(Node<Integer> root) {
        ArrayList<String> result = new ArrayList<>();
        if (root.val == null) {
            return result;
        }
        ArrayList<String> currentPath = new ArrayList<>();
        dfsAllPossiblePaths(root, currentPath, result);
        return result;
    }

    public static List<String> letterCombination(int n) {
        List<String> result = new ArrayList<>();
        letterCombinationDfs(result, new ArrayList<>(), 0, n);

        return result;
    }

    private static void letterCombinationDfs(List<String> result, List<String> currentPath, int startIndex, int n) {
        if (startIndex == n) {
            result.add(String.join("", currentPath));
            return;
        }

        for (String letter : new String[]{"A", "B"}) {
            currentPath.add(letter);
            letterCombinationDfs(result, currentPath, startIndex + 1, n);
            currentPath.remove(startIndex);
        }
    }

    // * ########################################################################
    private static void generateAllPossibleCombinationsFromAPhone(List<String> result, String phoneDigits, int currentIndex, StringBuilder path) {
        if (currentIndex > phoneDigits.length() - 1) {
            result.add(path.toString());
            return;
        }

        for (Character character : KEYBOARD.get(phoneDigits.charAt(currentIndex))) {
            path.append(character);
            generateAllPossibleCombinationsFromAPhone(result, phoneDigits, currentIndex + 1, path);
            path.deleteCharAt(path.length() - 1);
        }
    }

    private static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        generateAllPossibleCombinationsFromAPhone(result, digits, 0, new StringBuilder());
        return result;
    }


    private static final Map<Character, char[]> KEYBOARD = Map.of(
            '2', "abc".toCharArray(),
            '3', "def".toCharArray(),
            '4', "ghi".toCharArray(),
            '5', "jkl".toCharArray(),
            '6', "mno".toCharArray(),
            '7', "pqrs".toCharArray(),
            '8', "tuv".toCharArray(),
            '9', "wxyz".toCharArray()
    );

    public static class Node<T> {
        public T val;
        public List<Node<T>> children;

        public Node(T val) {
            this(val, new ArrayList<>());
        }

        public Node(T val, List<Node<T>> children) {
            this.val = val;
            this.children = children;
        }
    }


}
