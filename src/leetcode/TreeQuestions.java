package leetcode;

import leetcode.sub.ListNode;
import leetcode.sub.TreeNode;

import java.util.*;

public class TreeQuestions {
    public static void main(String[] args) {

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

    // * 1325. Delete leaves with a given value
    // * Time complexity: O(n)
    // * Space complexity: O(n)
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        removeLeafNodeHelper(root, target);
        if (root.val == target && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    private boolean removeLeafNodeHelper(TreeNode root, int target) {
        // Base case
        if (root == null) {
            return false; // Leaf node is reached
        }

        boolean leftRemoved = removeLeafNodeHelper(root.left, target);
        boolean rightRemoved = removeLeafNodeHelper(root.right, target);

        if (leftRemoved) {
            root.left = null;
        }

        if (rightRemoved) {
            root.right = null;
        }

        if (root.val == target && root.left == null && root.right == null) {
            return true;
        }

        return false;
    }

    // * 1367. Linked List in Binary Tree
    // * Time complexity: o(n * m) - n is the number of nodes in the linked list, m is the number of nodes in the binary tree
    // * Space complexity: o(n)
    boolean pathFound = false;

    public boolean isSubPath(ListNode head, TreeNode root) {
        ListNode dummy = head;
        isSubPathHelper(dummy, root);
        return pathFound;
    }

    public void isSubPathHelper(ListNode dummy, TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.val == dummy.val) {
            checkPath(dummy, root);
        }

        // Iterate down both paths
        isSubPathHelper(dummy, root.left);
        isSubPathHelper(dummy, root.right);
    }

    public void checkPath(ListNode dummy, TreeNode root) {
        if (dummy == null) {
            pathFound = true;
            return;
        }

        if (root == null) {
            return;
        }

        if (dummy.val != root.val) {
            // If it does not match, cut the execution and return it
            return;
        }

        dummy = dummy.next;
        // Iterate down both paths
        checkPath(dummy, root.left);
        checkPath(dummy, root.right);
    }

    // * 2583. Kth Largest Sum in a Binary Tree
    // * Time complexity: o(n log n);
    // * Space complexity: o(n)
    public long kthLargestLevelSum(TreeNode root, int k) {
        // * BFS to get sum for each level
        // * Heap to keep track of the kth largest sum
        PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> {
            return Long.compare(b, a);
        });
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        long levelSum = 0;

        while (!queue.isEmpty()) {
            int numOfNodes = queue.size();
            for (int i = 0; i < numOfNodes; i++) {
                TreeNode node = queue.poll();
                levelSum += (long) node.val;

                // Add it back to the queue
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            // Put the sum into the pq and reset it
            pq.offer(levelSum);
            levelSum = 0;
        }

        if (pq.size() < k) {
            return -1;
        }

        while (k - 1 > 0 && !pq.isEmpty()) {
            pq.poll();
            k--;
        }
        return pq.poll();
    }

    // * 993. Cousins in Binary Tree
    // * Time complexity: o(n)
    // * Space complexity: o(n);
    public boolean isCousins(TreeNode root, int x, int y) {
        // 1. Keep track of parent
        // 2. Make sure it is on the same level (bfs)
        // 3. Else return false;
        ArrayDeque<TreeNode[]> queue = new ArrayDeque();
        queue.offer(new TreeNode[]{null, root});
        HashSet<Integer> set = new HashSet<>(); // Keep track of the number of parents;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode[] val = queue.poll();
                if (val[0] != null && (val[1].val == x || val[1].val == y)) {
                    set.add(val[0].val);
                }

                if (val[1].left != null) queue.offer(new TreeNode[]{val[1], val[1].left});
                if (val[1].right != null) queue.offer(new TreeNode[]{val[1], val[1].right});
            }

            if (set.size() == 2) {
                return true;
            }
            set = new HashSet<>();
        }

        return false;
    }

    // * 2641. Cousins in Binary Tree II
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public TreeNode replaceValueInTree(TreeNode root) {
        // 1. Get the sum for each branch (Left & Right Node) - This will be used to sum other values
        // 2. For each level, keep track of the total value of the next level
        // 3. When it comes to the next level, simply set the values to be "total" - current
        // Repeat the process
        int currValue = 0;
        int nextValue = 0;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(0, root));

        while (!queue.isEmpty()) {
            int size = queue.size();
            currValue = nextValue;
            nextValue = 0;

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();

                // Add the children value of the current branch into the next value
                int branchValue = 0;
                if (node.treeNode.left != null) branchValue += node.treeNode.left.val;
                if (node.treeNode.right != null) branchValue += node.treeNode.right.val;
                nextValue += branchValue;

                if (node.treeNode.left != null) {
                    queue.offer(new Node(branchValue, node.treeNode.left));
                }
                if (node.treeNode.right != null) {
                    queue.offer(new Node(branchValue, node.treeNode.right));
                }

                // Now set the current node's value to be curr value - branch value;
                node.treeNode.val = currValue - node.branchValue;
            }
        }
        return root;
    }

    class Node {
        int branchValue;
        TreeNode treeNode;

        public Node(int branchValue, TreeNode treeNode) {
            this.branchValue = branchValue;
            this.treeNode = treeNode;
        }
    }

    // * 951. Flip Equivalent Binary Trees
    // * Time complexity: o(n) - Every node is visited at most twice
    // * Space complexity: o(n) - Longest path
    public boolean flipEquivHelper(TreeNode root1, TreeNode root2) {
        // If both are null, means its a match.
        if (root1 == null && root2 == null) {
            return true;
        }

        // If one is null and one isnt null, means false
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }

        // Compare both roots
        boolean combinationOne = flipEquivHelper(root1.left, root2.left) && flipEquivHelper(root1.right, root2.right);
        boolean combinationTwo = flipEquivHelper(root1.left, root2.right) && flipEquivHelper(root1.right, root2.left);

        return combinationOne || combinationTwo;
    }

    // * 103. Binary Tree Zig Zag Level Order Traversal
    // * Time complexity: o(n)
    // * Space complexity: o(n)
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        boolean leftToRight = true;
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> subResult = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                subResult.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            if (!leftToRight) {
                result.add(subResult.reversed());
            } else {
                result.add(subResult);
            }
            leftToRight = !leftToRight;
        }

        return result;
    }

}