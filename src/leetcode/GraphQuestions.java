package leetcode;

import java.util.*;

public class GraphQuestions {
    void main(String[] args) {
    }

    // * 200. Number of islands - BFS approach
    // * Time Complexity: o(n) - Visit every node once
    // * Space complexity: o(1)
    public int numIslands(char[][] grid) {
        int numRow = grid.length;
        int numCol = grid[0].length;
        int islands = 0;
        for (int r = 0; r < numRow; r++) {
            for (int c = 0; c < numCol; c++) {
                if (grid[r][c] == '0') continue;
                // Else execute the bfs and count
                numIslandsBFS(grid, new Coordinate(r, c), numRow, numCol);
                islands++;
            }
        }
        return islands;
    }

    private void numIslandsBFS(char[][] grid, Coordinate startingNode, int numRow, int numCol) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.offer(startingNode);
        grid[startingNode.r][startingNode.c] = '0'; // Set to be 0 to demarcate as "visited"
        while (!queue.isEmpty()) {
            Coordinate node = queue.poll();
            List<Coordinate> neighbours = getNumIslandsNeighbours(node, numRow, numCol);
            for (Coordinate neighbour : neighbours) {
                if (grid[neighbour.r][neighbour.c] == '0') continue;

                queue.offer(neighbour);
                grid[neighbour.r][neighbour.c] = '0';
            }
        }
    }

    private List<Coordinate> getNumIslandsNeighbours(Coordinate coord, int numRows, int numCols) {
        List<Coordinate> neighbours = new ArrayList<>();
        // Get Top, Down, Left and Right
        int[] deltaRow = {-1, 0, 1, 0}; // Directly either top or bottom
        int[] deltaCol = {0, 1, 0, -1}; // Directly either left or right
        for (int i = 0; i < deltaRow.length; i++) {
            int neighbourRow = coord.r + deltaRow[i];
            int neighbourCol = coord.c + deltaCol[i];
            if (neighbourCol >= 0 && neighbourCol < numCols && neighbourRow >= 0 && neighbourRow < numRows) {
                neighbours.add(new Coordinate(neighbourRow, neighbourCol));
            }
        }
        return neighbours;
    }

    // * 200 Number of Islands - DFS Approach
    // * Time complexity o(n)
    // * Space complexity o(n)
    public int numIslandsDFS(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    numIslandsDfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void numIslandsDfs(char[][] grid, int i, int j) {
        if (
                i < 0 ||
                        j < 0 ||
                        i >= grid.length ||
                        j >= grid[0].length ||
                        grid[i][j] == '0'
        ) {
            return;
        }
        grid[i][j] = '0';
        numIslandsDfs(grid, i + 1, j);
        numIslandsDfs(grid, i, j + 1);
        numIslandsDfs(grid, i - 1, j);
        numIslandsDfs(grid, i, j - 1);

        new HashMap<>().values();
    }

    // * 695. Max Area of Island
    // * Time complexity: o(n)
    // * Time complexity: o(n)
    public int maxAreaOfIsland(int[][] grid) {
        int maxValue = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                maxValue = Math.max(maxAreaIslandDfs(grid, i, j), maxValue);
            }
        }
        return maxValue;
    }

    private int maxAreaIslandDfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0) {
            return 0; // Don't count the current grid;
        }
        grid[i][j] = 0;
        return 1 + maxAreaIslandDfs(grid, i + 1, j) + maxAreaIslandDfs(grid, i, j + 1) + maxAreaIslandDfs(grid, i - 1,
                j) + maxAreaIslandDfs(grid, i, j - 1);
    }

    // * Wall and gates / Islands and treasures
    public void islandsAndTreasure(int[][] grid) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        int n = grid.length;
        int m = grid[0].length;

        // Add all the exit points to the queue first
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    queue.offer(new Coordinate(i, j));
                }
            }
        }

        while (!queue.isEmpty()) {
            Coordinate currentCoordinate = queue.poll();
            List<Coordinate> neighbours = islandAndTreasureGetNeighbours(currentCoordinate, n, m);
            for (Coordinate neighbour : neighbours) {
                if (grid[neighbour.r][neighbour.c] == Integer.MAX_VALUE) { // Means not visited
                    grid[neighbour.r][neighbour.c] = 1 + grid[currentCoordinate.r][currentCoordinate.c]; // Adding 1
                    // to the current coordinate's value and setting it to the neighbour
                    queue.add(neighbour);
                }
            }
        }
    }

    private List<Coordinate> islandAndTreasureGetNeighbours(Coordinate currentCoordinate, int n, int m) {
        List<Coordinate> neighbours = new ArrayList<>();
        int[] deltaRow = new int[]{0, 1, 0, -1};
        int[] deltaCol = new int[]{1, 0, -1, 0};

        for (int i = 0; i < deltaRow.length; i++) {
            int newRow = currentCoordinate.r + deltaRow[i];
            int newCol = currentCoordinate.c + deltaCol[i];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m) {
                neighbours.add(new Coordinate(newRow, newCol));
            }
        }
        return neighbours;
    }

    private static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    // * 133. Clone Graph
    // * Time complexity - o(n) Edge + Nodes
    // * Space complexity - o(n) Number of nodes
    // * Recommended approach: DFS, iterate through the whole node until all nodes has been "reached",
    // * approach works because this is a fully connected, undirected graph
    public Node cloneGraph(Node node) {
        // 1.   Maintain a hashmap to keep track of the created nodes
        //      This in turn helps us to prevent creating duplicate nodes
        Map<Integer, Node> oldNodeToNewNodeMap = new HashMap<>();
        return cloneGraphDfs(oldNodeToNewNodeMap, node);
    }

    public Node cloneGraphDfs(Map<Integer, Node> oldNodeToNewNodeMap, Node oldNode) {
        if (oldNodeToNewNodeMap.containsKey(oldNode.val)) {
            return oldNodeToNewNodeMap.get(oldNode.val); // If node already exist, simply return it
        }
        // 2.   For every new node created, add it to the hashmap
        Node copy = new Node(oldNode.val);
        oldNodeToNewNodeMap.put(oldNode.val, copy); // Else create a new copy

        // 3.   Afterwards, simply execute DFS to go through neighbours and add it to the copy's neighbours
        for (Node neighbour : oldNode.neighbors) {
            // The process is repeated for each neighbour
            copy.neighbors.add(cloneGraphDfs(oldNodeToNewNodeMap, neighbour));
        }
        return copy;
    }

    private static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    // * 994. Rotting oranges
    // * Time complexity
    // * Space complexity
    public int orangesRotting(int[][] grid) {
        // Similar to getting the closest distance to each gate, but this time only find the oranges
        // 1. Get the positions of all rotten oranges
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        int maxRow = grid.length;
        int maxCol = grid[0].length;
        int fresh = 0; // Keeps track the number of fresh oranges
        fresh = getFreshAndRottingOranges(grid, maxRow, maxCol, queue, fresh);
        int count = 0;

        // 2. Execute BFS on each rotten oranges concurrently
        // 3. Add corresponding oranges into the queue
        // 4. Repeat until queue is empty
        while (!queue.isEmpty() && fresh != 0) {
            count += 1;
            int rottenOranges = queue.size();
            for (int i = 0; i < rottenOranges; i++) {
                Coordinate currOrange = queue.poll();
                List<Coordinate> currOrangeNeighbours = getOrangeNeighbour(currOrange, maxRow, maxCol);
                for (Coordinate neighbour : currOrangeNeighbours) {
                    if (grid[neighbour.r][neighbour.c] == 1) {
                        grid[neighbour.r][neighbour.c] = 2;
                        queue.add(neighbour);
                        fresh -= 1;
                    }
                }
            }
        }

        // 5. Return result based on the number of fresh oranges left
        return fresh == 0 ? count : -1;
    }

    private static int getFreshAndRottingOranges(int[][] grid, int maxRow, int maxCol, ArrayDeque<Coordinate> queue, int fresh) {
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++) {
                if (grid[r][c] == 2) {
                    queue.offer(new Coordinate(r, c));
                }
                if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }
        return fresh;
    }

    private List<Coordinate> getOrangeNeighbour(Coordinate currCoordinate, int maxRow, int maxCol) {
        List<Coordinate> neighbours = new ArrayList<>();
        int[] deltaRow = new int[]{0, 1, 0, -1};
        int[] deltaCol = new int[]{1, 0, -1, 0};

        for (int i = 0; i < deltaRow.length; i++) {
            int newRow = currCoordinate.r + deltaRow[i];
            int newCol = currCoordinate.c + deltaCol[i];
            if (newRow >= 0 && newRow < maxRow && newCol >= 0 && newCol < maxCol) {
                neighbours.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbours;
    }

    // * 130. Surrounded Regions
    // * Time complexity - o(n)
    // * Space complexity - o(n) - Calls
    public void solve(char[][] board) {
        // Question is similar to number of islands but with a twist - It must be fully surrounded by water
        // Instead of counting the number of islands, we can reverse engineer it
        // Get rid of the islands on the border, afterwhich you can just set the rest to be surrounded

        // Top and bottom row;
        int maxRow = board.length;
        int maxCol = board[0].length;

        // Top and bottom row
        for (int i = 0; i < maxCol; i++) {
            if (board[0][i] == 'O') {
                solverHelper(board, 0, i);
            }
            if (board[maxRow - 1][i] == 'O') {
                solverHelper(board, maxRow - 1, i);
            }
        }

        // Left and Right col
        for (int i = 0; i < maxRow; i++) {
            if (board[i][0] == 'O') {
                solverHelper(board, i, 0);
            }
            if (board[i][maxCol - 1] == 'O') {
                solverHelper(board, i, maxCol - 1);
            }
        }

        // Once both iterations is done, set all Ts back to O and the rest to be X
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                if (board[row][col] == 'T') {
                    board[row][col] = 'O';
                } else {
                    board[row][col] = 'X';
                }
            }
        }
    }

    public void solverHelper(char[][] board, int currRow, int currCol) {
        if (currRow >= board.length || currRow < 0 || currCol >= board[0].length || currCol < 0) {
            return;
        }

        if (board[currRow][currCol] == 'O') { // Means it is part of an island on the border
            board[currRow][currCol] = 'T'; // Set it to be T
            solverHelper(board, currRow - 1, currCol);
            solverHelper(board, currRow, currCol - 1);
            solverHelper(board, currRow + 1, currCol);
            solverHelper(board, currRow, currCol + 1);
        }
    }


    // * 3243. Shortest Distance After Road Addition Queries I
    // * Time complexity - o(n * q) - n is the number of nodes, q is the number of queries
    // * Space complexity - o(n)
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        // HashMap to keep track of the next node
        // Overall time complexity would be n * q, because we need to repeat the bfs for at least the q number of times
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (i + 1 == n) {
                map.put(i, new ArrayList<>());
                continue;
            }
            map.put(i, new ArrayList<>(List.of(i + 1)));
        }

        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            // add current point to the hashmap
            map.get(query[0]).add(query[1]);

            // Proceed to do BFS to find the shortest path
            result[i] = shortestDistanceAfterQueries(map, n - 1);
        }
        return result;
    }

    public int shortestDistanceAfterQueries(Map<Integer, List<Integer>> map, int target) {
        Set<Integer> visited = new HashSet<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(0); // Starting point;
        int distance = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Integer curr = queue.poll();
                if (curr == target) {
                    return distance;
                }
                for (int neighbour : map.get(curr)) {
                    if (visited.contains(neighbour)) {
                        continue;
                    }
                    queue.offer(neighbour);
                }
                visited.add(curr);
            }
            distance++;
        }
        return distance;
    }

}
