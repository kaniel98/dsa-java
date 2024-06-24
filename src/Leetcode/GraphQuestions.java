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

    public void numIslandsDfs(char[][] grid, int i, int j) {
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


    private static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
