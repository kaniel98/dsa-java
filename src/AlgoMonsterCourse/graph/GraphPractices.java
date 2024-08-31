package AlgoMonsterCourse.graph;

import java.util.*;

public class GraphPractices {
    public static void main(String[] args) {

    }

    // * Get the shortest path from one node to a target node
    // * Time complexity - o(n+m) where n = nodes and m = edges
    // * Space complexity - o(n) where n is the nodes in the DataStructures.queue
    public static int shortestPath(List<List<Integer>> graph, int a, int b) {
        if (graph.get(a) == null || graph.get(b) == null) {
            return -1;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(a);
        Set<Integer> visited = new HashSet<>();
        visited.add(a);
        int level = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) { // Only iterate for that current "level"
                int node = queue.poll();
                if (node == b) return level; // If match, return
                for (int neighbour : graph.get(node)) {
                    if (visited.contains(neighbour)) continue;
                    queue.offer(neighbour);
                    visited.add(neighbour);
                }
            }
            level++;
        }
        return level;
    }

    // * 733. Flood Fill
    // * Time complexity - o(r * c)
    // * Space complexity - o(r*c)
    public static int[][] floodFill(int[][] image, int r, int c, int replacement) {
        Coordinate root = new Coordinate(r, c);
        int numCols = image[0].length;
        int numRows = image.length;
        floodFillBfs(image, root, replacement, numRows, numCols);
        return image;
    }

    private static void floodFillBfs(int[][] image, Coordinate root, int replacementColor, int numRows,
                                     int numCols) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean[][] visited = new boolean[numRows][numCols];
        int rootColor = image[root.r][root.c]; // Get that color to be change
        image[root.r][root.c] = replacementColor; // Replace it with the given replacement color
        visited[root.r][root.c] = true; // Set it to be visited
        while (!queue.isEmpty()) {
            Coordinate node = queue.poll(); // Get current node
            List<Coordinate> neighbours = floodFillGetNeighbours(image, node, rootColor, numRows, numCols);
            for (Coordinate neighbour : neighbours) {
                if (visited[neighbour.r][neighbour.c]) {
                    continue;
                }
                image[neighbour.r][neighbour.c] = replacementColor;
                queue.offer(neighbour);
                visited[neighbour.r][neighbour.c] = true;
            }
        }
    }

    // Helper function to get the coordinates
    private static List<Coordinate> floodFillGetNeighbours(int[][] image, Coordinate node, int rootColor, int numRows,
                                                           int numCols) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[] deltaRow = {-1, 0, 1, 0}; // Directly either top or bottom
        int[] deltaCol = {0, 1, 0, -1}; // Directly either left or right
        for (int i = 0; i < deltaRow.length; i++) {
            int neighborRow = node.r + deltaRow[i];
            int neighborCol = node.c + deltaCol[i];
            // Make sure not out of bound
            if (0 <= neighborRow && neighborRow < numRows && 0 <= neighborCol && neighborCol < numCols) {
                // Check if the neighbour has the same color
                if (image[neighborRow][neighborCol] == rootColor) {
                    neighbors.add(new Coordinate(neighborRow, neighborCol));
                }
            }
        }
        return neighbors;
    }

    private static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    // * 200. Number of Islands
    // * Time complexity - o(r * c)
    // * Space complexity - o(r * c)
    public static int numIslands(char[][] grid) {
        // 1. Get root
        // 2. BFS to repeat until the whole island is covered
        // 3. Get neighbours
        int numRows = grid.length;
        int numCols = grid[0].length;
        int count = 0;
        for (int r = 0; r < numRows; r++) { // Iterate through each row
            for (int c = 0; c < numCols; c++) { // Iterate through each col
                if (grid[r][c] == '0') continue;
                countNumberOfIslandsBFS(grid, new Coordinate(r, c), numRows, numCols);
                count++; // After each bfs is executed, an island would have been found
            }
        }
        return count;
    }

    private static void countNumberOfIslandsBFS(char[][] grid, Coordinate root, int numRows, int numCols) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.offer(root);
        grid[root.r][root.c] = '0'; // Set it to be 0 to indicate that this node has been visited
        // No need to maintain another map - Reduces memory required
        while (!queue.isEmpty()) {
            Coordinate node = queue.poll();
            List<Coordinate> neighbours = numberOfIslandsNeighbours(grid, numRows, numCols, node);
            for (Coordinate neighbour : neighbours) {
                if (grid[neighbour.r][neighbour.c] == '0') continue; // Means the node is either visited / not
                // an island, continue
                queue.offer(neighbour);
                grid[neighbour.r][neighbour.c] = '0'; // Set it as visited
            }
        }
    }

    private static List<Coordinate> numberOfIslandsNeighbours(char[][] grid, int numRows,
                                                              int numCols, Coordinate node) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[] deltaRow = {-1, 0, 1, 0}; // Directly either top or bottom
        int[] deltaCol = {0, 1, 0, -1}; // Directly either left or right
        for (int i = 0; i < deltaRow.length; i++) {
            int neighborRow = node.r + deltaRow[i];
            int neighborCol = node.c + deltaCol[i];
            // Make sure not out of bound
            if (0 <= neighborRow && neighborRow < numRows && 0 <= neighborCol && neighborCol < numCols) {
                neighbors.add(new Coordinate(neighborRow, neighborCol));
            }
        }
        return neighbors;
    }

    // * 1197. Knight Minimum moves
    // * Time complexity - o(|x| * |y|)
    // * Space complexity - o(|x| * |y|)
    public static int getKnightShortestPath(int x, int y) {
        // Get neighbours - Need to change
        // Apply BFS Graph template - Get neighbour & BFS
        return getKnightShortestPathBFS(new Coordinate(0, 0), x, y);
    }

    private static int getKnightShortestPathBFS(Coordinate start, int targetX, int targetY) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.offer(start);
        int steps = 0;
        // Maintain a hashset to keep track if a coordinate has been visited
        HashSet<Coordinate> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            int n = queue.size(); // For that current level only
            for (int i = 0; i < n; i++) {
                Coordinate node = queue.poll();
                if (node.r == targetX && node.c == targetY) {
                    return steps;
                }
                List<Coordinate> neighbours = getKnightShortestPathNeighbours(node);
                for (Coordinate neighbour : neighbours) {
                    if (visited.contains(neighbour)) continue; // Dont need to visit again
                    queue.offer(neighbour);
                    visited.add(neighbour);
                }
            }
            steps++;
        }
        return steps;
    }

    private static List<Coordinate> getKnightShortestPathNeighbours(Coordinate coordinate) {
        List<Coordinate> neighbours = new ArrayList<>();
        int[] deltaRow = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};
        int[] deltaCol = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
        for (int i = 0; i < deltaRow.length; i++) {
            int r = coordinate.r + deltaRow[i];
            int c = coordinate.c + deltaCol[i];
            neighbours.add(new Coordinate(r, c));
        }
        return neighbours;
    }

    // * 286. Walls and Gates
    // * Time complexity - o(nm)
    // * Space complexity - o(nm)
    public static List<List<Integer>> mapGateDistances(List<List<Integer>> dungeonMap) {
        // * Not ideal to do bfs for each node (Lots of repeated iterations)
        // Ideal to start from gates instead
        // Simultaneously doing BFS from each gate (Do not update the coordinate if it is not max value)

        // 1. Get all gates in the dungeon map and add it to the DataStructures.queue
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        int n = dungeonMap.size(), m = dungeonMap.getFirst().size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dungeonMap.get(i).get(j) == 0) { // It is a gate
                    queue.offer(new Coordinate(i, j)); // Offer is used to add a new node into DataStructures.queue
                }
            }
        }
        // 2. Go through the DataStructures.queue and update the map
        while (!queue.isEmpty()) {
            Coordinate currentCoordinate = queue.poll(); // Get the first node and removes from DataStructures.queue
            List<Coordinate> neighbours = getNeighboursMapGateDistance(currentCoordinate, n, m);
            for (Coordinate neighbour : neighbours) {
                if (dungeonMap.get(neighbour.r).get(neighbour.c) == Integer.MAX_VALUE) { // Means not visited
                    dungeonMap.get(neighbour.r).set(neighbour.c, 1 + dungeonMap.get(currentCoordinate.r).get(currentCoordinate.c));
                    // Set it as +1 of the current coordinate's value
                    queue.add(neighbour);
                }
            }
        }
        // WRITE YOUR BRILLIANT CODE HERE
        return dungeonMap;
    }

    private static List<Coordinate> getNeighboursMapGateDistance(Coordinate currCoordinate, int n, int m) {
        List<Coordinate> neighbours = new ArrayList<>();
        List<Coordinate> directions = List.of(new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(0, -1), new Coordinate(-1, 0));
        for (Coordinate direction : directions) {
            Coordinate newCoordinate = new Coordinate(currCoordinate.r + direction.r, currCoordinate.c + direction.c);
            // Check if the new coordinate is out of bounds
            if (newCoordinate.r >= 0 && newCoordinate.r < n && newCoordinate.c >= 0 && newCoordinate.c < m) {
                neighbours.add(newCoordinate);
            }
        }
        return neighbours;
    }

}
