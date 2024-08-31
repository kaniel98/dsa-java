package AlgoMonsterCourse.backtracking;

public class DFSBinaryTree {
    public static void main(String[] args) {

    }

    // Time Complexity o(N)
    // Space Complexity o(h) - Recursive calls will not exceed height
    public static boolean validBst(Node<Integer> root, int min, int max) {
        // * Define the return value
        if (root == null) {
            return true;
        }

        // * Define the returning state
        // Need to provide the min and max to know if the node is in the right position
        // root.val must always be lesser than max and bigger than minimum
        if (!(min < root.val && root.val < max)) {
            return false;
        }
        return validBst(root.left, min, root.val) && validBst(root.right, root.val, max);
    }

    public static boolean checkValidBst(Node<Integer> root) {
        return validBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
}
