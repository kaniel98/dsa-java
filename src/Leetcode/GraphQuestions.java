package Leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class GraphQuestions {
    public static void main(String[] args) {

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
}
