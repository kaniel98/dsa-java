package algorithms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class DepthFirstSearch {
    public static void main(String[] args) {
    }

    public static Node<Integer> dfs(Node<Integer> root, Integer target) {
        // If target == root, return it
        if (Objects.equals(root.val, target)) {
            return root;
        }

        // Recursively call towards the left -  return non-null return value from the recursive calls
        Node<Integer> left = dfs(root.left, target);
        if (left != null) {
            return left;
        }
        // * At this point, left is null - Right is either null or not null
        // * Right node is returned directly because
        // * If not null, we should return it
        // * If it is null, both left and right is null thus we need to return it
        return dfs(root.right, target);
    }

    public static int treeMaxDepthDfs(Node<Integer> root) {
        // * 1. Determine the return value
        // If the node is null, return 0
        if (root == null) {
            return 0;
        }

        // * 2. Identify the states
        // Return + 1 to represent the increase in height
        return Math.max(treeMaxDepthDfs(root.left), treeMaxDepthDfs(root.right)) + 1;
    }

    // Time complexity o(n) - Traverse n nodes & n-1 edges
    // Space complexity o(h) - Height. but it can be o(n) if the tree is skewed
    public static int treeMaxDepth(Node<Integer> root) {
        return (root != null) ? treeMaxDepthDfs(root) - 1 : 0;
    }

    // * Get the max from root to leaf node
    public static int visibleTreeNodeDfs(Node<Integer> root, int maxSoFar) {
        // * 1. Determine the return value
        // If it is null, return 0
        if (root == null) return 0;
        int total = 0;
        if (root.val >= maxSoFar) {
            total += 1; // The current node is visible
            maxSoFar = Math.max(maxSoFar, root.val); // Update the max value
        }

        // * 2 Determine the states
        total += visibleTreeNodeDfs(root.left, maxSoFar);
        total += visibleTreeNodeDfs(root.right, maxSoFar);

        return total;
    }

    public static int visibleTreeNode(Node<Integer> root) {
        return visibleTreeNodeDfs(root, Integer.MIN_VALUE);
    }

    // * Check if the BFS tree is balanced
    // Time complexity - o(n)
    // Space complexity - o(h) or o(n)
    public static int isBalancedDfs(Node<Integer> tree) {
        // * Identify the return value - Current height of the tree to the parents
        if (tree == null) {
            return 0; // Assuming this is the root node with no height
        }

        // * Identify the return states - Return the current height
        // * If it is identified to be more than 1, return it straight away
        // * Else, return the max height + 1 (Including itself)
        int left = isBalancedDfs(tree.left);
        int right = isBalancedDfs(tree.right);

        if (left == -1 || right == -1) {
            return -1;
        }
        // Check the difference between the two
        if (Math.abs(left - right) > 1) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }

    public static boolean isBalanced(Node<Integer> tree) {
        return isBalancedDfs(tree) != -1;
    }

    // * Serialize a tree - Convert from BST to String representation
    public static String serializeTree(Node<Integer> root) {
        StringJoiner res = new StringJoiner(" ");
        serializeDFS(root, res);
        return res.toString();
    }

    public static void serializeDFS(Node<Integer> root, StringJoiner result) {
        // Define the return
        if (root == null) {
            result.add("x");
            return;
        }

        // Pre Order Traversal - Current, Left, Right
        result.add(Integer.toString(root.val));
        serializeDFS(root.left, result);
        serializeDFS(root.right, result);
    }

    // * Convert a String representation into a tree
    public static Node<Integer> deserializeTree(Iterator<String> nodes) {
        String val = nodes.next();
        if (val.equals("x")) {
            return null;
        }
        Node<Integer> curr = new Node<>(Integer.parseInt(val));
        curr.left = deserializeTree(nodes);
        curr.right = deserializeTree(nodes);
        return curr;
    }

    public static Node<Integer> deserialize(String result) {
        return deserializeTree(Arrays.stream(result.split(" ")).iterator());
    }

    // * Invert a binary tree - Left and right nodes switch
    public static Node<Integer> invertBinaryTree(Node<Integer> tree) {
        if (tree == null) {
            return null;
        }
        return new Node<>(tree.val, invertBinaryTree(tree.right), invertBinaryTree(tree.left));
    }

    // * Insert a node into the BST
    // Time complexity - o(log n)
    // Space complexity - o(h)
    public static Node<Integer> insertBst(Node<Integer> tree, int value) {
        // * Determine the return state - If it is null, insert the value as a new node
        if (tree == null) {
            return new Node<>(value);
        }
        // Determine the return state - Repeat this step for left & right, depending on if the value is more thn or less than th current value
        if (tree.val == value) {
            return tree;
        } else if (tree.val > value) { // Left side
            tree.left = insertBst(tree.left, value);
        } else {
            tree.right = insertBst(tree.right, value);
        }
        // At the end, return the tree
        return tree;
    }

    // * Find the lowest common ancestor of a binary search tree
    public static int lcaOnBst(Node<Integer> bst, int p, int q) {
        // Solve on the basis that for any given node with value a
        // The value on the left side is always less than a
        // The value on the right side is always more than a

        // Cases should be broken down as such:
        // 1. If p & q are on the left side, continue search left
        // 2. If p & q are on the right side, continue search right
        // 3. If p & q are split - Either equal or more/less, return the current node as LCA
        // Edge condition: If p / q is the current node val, can return it because it is determined as its own descendant
        // * Define the return state
        // * Assume that p is smaller or equal
        // * Assume that q is bigger or equal
        if (p < bst.val && q < bst.val) { // Case 1: p & q is on left side
            return lcaOnBst(bst.left, p, q);
        } else if (p > bst.val && q > bst.val) { // Case 2: if p & q are on right side
            return lcaOnBst(bst.right, p, q);
        } else {
            return bst.val;
        }
    }

    // * Find the lowest common ancestor of a binary tree
    // Adopt post order traversal - Left & Right first before current
    // You can assume each node value in the tree is unique and that both target nodes are guaranteed to exist in the tree.
    public static Node lca(Node root, Node node1, Node node2) {
        // * Determine the return state
        // Actions for the current node:
        // Case 1: if the node is null: return null
        if (root == null) {
            return null;
        }
        // Case 2: If the node is either node1 or node2; return it
        if (root == node1 || root == node2) {
            return root;
        }
        // Case 3: Proceed to evaluate its children
        // If the node is neither node1 or node 2 - Evaluate its children
        Node left = lca(root.left, node1, node2);
        Node right = lca(root.right, node1, node2);
        // If both subtrees return non null: return the subtree itself
        if (left != null && right != null) {
            return root;
        }
        // If subtree contains one non null and one null: return the non null
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }

        // If both left & right is null: return null
        return null;
    }


    public static class Node<Integer> {
        public Integer val;
        public Node<Integer> left;
        public Node<Integer> right;

        public Node(Integer val) {
            this(val, null, null);
        }

        public Node(Integer val, Node<Integer> left, Node<Integer> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
