package Leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        inorderTraversal(root, path, k);
        return path.get(k - 1);
    }

    public void inorderTraversal(TreeNode root, ArrayList<Integer> path, int k) {
        if (root == null) {
            return;
        }
        if (path.size() == k) {
            return;
        }

        inorderTraversal(root.left, path, k);
        path.add(root.val);
        inorderTraversal(root.right, path, k);
    }

    // * 105. Construct Binary Tree from Pre-order and In-order traversal
    // * Time complexity - o (n)
    // * Space complexity - o (n log n)
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Preorder - Root, Left, Right
        // Inorder - Left, Root, Right

        // * Preorder - [3,9,20,15,7]
        // * Inorder - [9,3,15,20,7]

        // * Values in this binary search tree is always unique
        // * 1. First value in pre-order will always be the root
        // * 2. This means that values to the left of root in the inorder traversal = Left sub tree & right of root =
        // *    Right sub tree
        // * 3. This means that we can always reconstruct the binary tree by tracing left

        // Return case
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        // Set the root to be the first value of pre-order
        TreeNode root = new TreeNode(preorder[0]);

        // Find the index of the root in the in-order (It will be the left sub tree)
        int mid = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (preorder[0] == inorder[i]) {
                mid = i;
                break;
            }
        }

        // Set the left side of the root
        root.left = buildTree(Arrays.copyOfRange(preorder, 1, mid + 1), Arrays.copyOfRange(inorder, 0, mid));
        // Set the right side of the root
        root.right = buildTree(Arrays.copyOfRange(preorder, mid + 1, preorder.length), Arrays.copyOfRange(inorder,
                mid + 1, inorder.length));
        return root;
    }

    // * 1448. Count good nodes in a binary tree
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public int goodNodes(TreeNode root) {
        return goodNodesDfs(root, root.val);
    }

    public int goodNodesDfs(TreeNode current, int currentMax) {
        if (current == null) {
            return 0;
        }
        int res = current.val >= currentMax ? 1 : 0;
        currentMax = Math.max(current.val, currentMax);
        res += goodNodesDfs(current.left, currentMax);
        res += goodNodesDfs(current.right, currentMax);
        return res;
    }

    // * 572. Subtree of another tree
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // Return case
        if (subRoot == null || isSameTreeHelper(root, subRoot)) return true;
        // If the root is null - Means false
        if (root == null) return false;
        // Else proceed to check the left and right side
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTreeHelper(TreeNode root, TreeNode subroot) {
        if (root == null && subroot == null) {
            return true;
        }
        // If the two values don't match = No longer the same tree
        if (root == null || subroot == null || root.val != subroot.val) {
            return false;
        }
        return isSameTreeHelper(root.left, subroot.left) && isSameTreeHelper(root.right, subroot.right);
    }

    // * 102. Binary tree level order traversal
    // * Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
    // * Time complexity - o(n)
    // * Space complexity - o(n)
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        levelOrderDfs(root, result, 0);
        return result;
    }

    private void levelOrderDfs(TreeNode curr, List<List<Integer>> result, int currentLevel) {
        if (curr == null) {
            return;
        }
        // Based on the current level, add the current val into the "result"
        if (result.size() < currentLevel + 1) {
            result.add(new ArrayList<>());
        }
        result.get(currentLevel).add(curr.val);
        // Proceed to iterate for the following levels (+1)
        levelOrderDfs(curr.left, result, currentLevel + 1);
        levelOrderDfs(curr.right, result, currentLevel + 1);
    }

    // * 199. Binary tree right side view
    // * Time complexity - o(n)
    // * Space complexity - o(n) (Length of the path)
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        // Basically we always want the right most node
        rightSideViewBFS(root, result);
        return result;
    }

    private void rightSideViewBFS(TreeNode curr, List<Integer> result) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(curr);
        while (!queue.isEmpty()) {
            int n = queue.size(); // Current Tree nodes of the current val
            result.add(queue.peek().val); // Adding the right most value into the DataStructures.queue
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.pop();
                if (node.right != null) queue.add(node.right); // Ensures that the right most will always be at the
                // start
                if (node.left != null) queue.add(node.left);
            }
        }
    }

    // * 701. Insert into a binary search tree
    // * Time complexity - o(n)
    // * Space complexity - o(1)
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode temp = root;
        if (temp == null) return new TreeNode(val);

        while (temp != null) {
            if (val < temp.val) {
                if (temp.left == null) {
                    temp.left = new TreeNode(val);
                    break;
                }
                temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.right = new TreeNode(val);
                    break;
                }
                temp = temp.right;
            }
        }
        return root;
    }

    // * 450. Delete node in BST
    public TreeNode deleteNode(TreeNode root, int key) {
        return null;
    }
}