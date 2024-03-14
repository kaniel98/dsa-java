package binaryTree;

public class BinaryTree {

    // * Left, current, right
    public static void inOrderTraversal(Node<Integer> root) {
        inOrderTraversal(root.left);
        System.out.println(root.val);
        inOrderTraversal(root.right);
    }

    // * Current, Left, Right
    public static void preOrderTraversal(Node<Integer> root) {
        System.out.println(root.val);
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }


    // * Left, Right, Current
    public static void postOrderTraversal(Node<Integer> root) {
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.println(root.val);
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
