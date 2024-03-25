package algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BreathFirstSearch {
    public static void main(String[] args) {

    }

    public static class Node<T> {
        public T val;
        public Node<T> left;
        public Node<T> right;

        public Node(T val) {
            this(val, null, null);
        }

        public Node(T val, Node<T> left, Node<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // * Basic binary tree BFS Traversal
    public static List<List<Integer>> levelOrderTraversal(Node<Integer> root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();

        // Start by adding the root node in
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> newLevel = new ArrayList<>();
            for (int i = 0; i < n; i++) { // Just iterate for the current level
                Node<Integer> node = queue.pop();
                newLevel.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            res.add(newLevel);
        }
        return res;
    }

    // * Return a tree in BFS but zig zag
    public static List<List<Integer>> zigZagTraversal(Node<Integer> root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
        boolean isReverse = false;

        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            ArrayDeque<Integer> currentLevel = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                Node<Integer> node = queue.pop();
                if (isReverse) {
                    currentLevel.addFirst(node.val);
                } else {
                    currentLevel.add(node.val);
                }
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            res.add(Arrays.asList(currentLevel.toArray(new Integer[0])));
            isReverse = !isReverse;
        }
        return res;
    }

    // * Return the binary tree right side view
    public static List<Integer> binaryTreeRightSideView(Node<Integer> root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            res.add(queue.peek().val); // Adds the root at the start of queue
            // Proceed to pop the rest and add back the corresponding children, makes sure that right is add first so that it will be the first to pop
            for (int i = 0; i < n; i++) {
                Node<Integer> node = queue.pop();
                if (node.right != null) queue.add(node.right);
                if (node.left != null) queue.add(node.left);
            }
        }
        return res;
    }

    // * Return the tree min depth
    // Time complexity - o(n)
    // Space complexity - o(n)
    public static Integer binaryTreeMinDepth(Node<Integer> root) {
        int currentDepth = 0;
        if (root == null) return currentDepth;
        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Node<Integer> node = queue.pop();
                if (node.left == null && node.right == null) {
                    return currentDepth;
                }
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            currentDepth++;
        }
        return currentDepth;
    }

}
