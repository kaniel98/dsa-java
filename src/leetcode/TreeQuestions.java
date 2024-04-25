package leetcode;

import java.util.ArrayList;

public class TreeQuestions {
    public static void main(String[] args) {

    }

    // Used to represent one node in a tree
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // * 226 Invert Binary Tree
    // * Time Complexity - o(n)
    // * Space complexity - o(n) - Longest path down
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null; // Return case
        TreeNode temp = new TreeNode(root.val);
        temp.right = invertTree(root.left);
        temp.left = invertTree(root.right);
        return temp;
    }

    // * 104. Maximum Depth of Binary Tree
    // * Time complexity - o(n)
    // * Space complexity - o(n) - Longest path
    public int maxDepth(TreeNode root) {
        if (root == null) return 0; // Root state
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // * 543. Diameter of Binary Tree
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    private int result = -1;

    public int diameterOfBinaryTree(TreeNode root) {
        diameterOfBinaryTreeDfs(root);
        return result;
    }

    private int diameterOfBinaryTreeDfs(TreeNode root) {
        // Break it down into smaller sub problems - What is the diameter at left and right nodes
        // Return diameter and depth
        // If diameter is not bigger thn sub diameter, + 1 to max depth instead
        if (root == null) return -1; // Shows that it is the root
        int left = 1 + diameterOfBinaryTreeDfs(root.left); // Depth of left
        int right = 1 + diameterOfBinaryTreeDfs(root.right); // Depth of right
        result = Math.max(result, left + right); // Check if diameter is bigger
        return Math.max(left, right); // Return the largest diameter
    }

    // * 235. Lowest common ancestor of a binary search tree
    // * Time complexity - o(log n) (Only visiting one node per level)
    // * Space complexity - o(1) no additional structures, but the maximum amount of calls can be up to n
    public TreeNode lowestCommonAncestorOfBinarySearchTree(TreeNode root, TreeNode p, TreeNode q) {
        // Case 1. When p & q are on the same side (e.g., left) search that side
        // Case 2. If p & q are split - either equal or more or less, return the current node as the LCA
        // Case 3: (Edge case) If p or q is the current node val, can return it straight because it is determined as
        // its own descendant
        if (p.val < root.val && q.val < root.val) { // Repeat on that left side
            return lowestCommonAncestorOfBinarySearchTree(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorOfBinarySearchTree(root.right, p, q);
        }
        // At this point in time, the two nodes would have either been split or one of the node is the current node
        // Return it straight
        return root;
    }

    // * 236. Lowest common ancestor of binary tree
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public TreeNode lowestCommonAncestorOfBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        // Return cases
        if (root == null) {
            return null;
        }
        // If root matches either p or q, return it
        if (root == p || root == q) {
            return root;
        }

        // Proceed to evaluate the children
        TreeNode left = lowestCommonAncestorOfBinaryTree(root.left, p, q);
        TreeNode right = lowestCommonAncestorOfBinaryTree(root.right, p, q);

        // If both are not null, means both are either p & q
        if (left != null && right != null) {
            return root;
        }
        // Else return which is not null to check for its parent
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    // * 110. Balanced Binary tree - Check if left tree and right tree max diff in height is 1
    // * Time Complexity - o (n)
    // * Space complexity - o (n)
    public boolean isBalanced(TreeNode root) {
        return (Boolean) isBalancedDfs(root)[0];
    }

    public Object[] isBalancedDfs(TreeNode root) {
        if (root == null) return new Object[]{true, 0};
        Object[] left = isBalancedDfs(root.left);
        Object[] right = isBalancedDfs(root.right);

        boolean isBalanced =
                (boolean) left[0] && (boolean) right[0] && (Math.abs((Integer) right[1] - (Integer) left[1]) <= 1);

        // Proceed to return it
        return new Object[]{isBalanced, 1 + Math.max((Integer) right[1], (Integer) left[1])};
    }

    // * 100. Same tree - Check if they are the same tree
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        return (p.val == q.val) && (isSameTree(p.right, q.right)) && isSameTree(p.left, q.right);
    }

    // * 98. Validate Binary Tree
    // * Time complexity - o(N)
    // * Space complexity - o(N)
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        return isValidBSTDFS(root, null, null);
    }

    private boolean isValidBSTDFS(TreeNode root, Integer min, Integer max) {
        // * Binary tree - Left must be smaller than current & Right must be larger than current
        // Return case
        if (root == null) {
            return true;
        }

        // The root must always be within the value of the min and max of that binary tree  - E.g., 5 Root, means the
        // right tree's left root must be smaller
        if (min != null && root.val <= min || max != null && root.val >= max) {
            return false;
        }

        // * Going down left, means the current will become the largest
        // * Going down right means the current will become the smallest
        return isValidBSTDFS(root.left, min, root.val) && isValidBSTDFS(root.right, root.val, max);
    }

    // * 230. Kth smallest element in a BST
    // * Time complexity - o(n) (Need to visit all of the nodes)
    // * Space complexity - o(n) (Need to store all of the values)
    public int kthSmallest(TreeNode root, int k) {
        // Get the inorder traversal of the tree and return the nth node from the end
        ArrayList<Integer> path = new ArrayList<>();
        inorderTraversal(root, path);
        return path.get(k);
    }

    public void inorderTraversal(TreeNode root, ArrayList<Integer> path) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, path);
        path.add(root.val);
        inorderTraversal(root.right, path);
    }

}
