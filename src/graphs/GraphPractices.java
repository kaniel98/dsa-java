package graphs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphPractices {
    public static void main(String[] args) {

    }

    // * Get the shortest path from one node to a target node
    // * Time complexity - o(n+m) where n = nodes and m = edges
    // * Space complexity - o(n) where n is the nodes in the queue
    public static int shortestPath(List<List<Integer>> graph, int a, int b) {
        if (graph.get(a) == null || graph.get(b) == null) {
            return -1;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(a);
        Set<Integer> visited = new HashSet<>();
        visited.add(a);
        int level = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) { // Only iterate for that current "level"
                int node = queue.pop();
                if (node == b) return level; // If match, return
                for (int neighbour : graph.get(node)) {
                    if (visited.contains(neighbour)) continue;
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
            level++;
        }
        return level;
    }
}
