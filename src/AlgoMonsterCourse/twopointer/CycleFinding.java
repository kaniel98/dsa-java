package AlgoMonsterCourse.twopointer;

public class CycleFinding {
    public static void main(String[] args) {

    }

    public static class Node<T> {
        public T val;
        public Node<T> next;

        public Node(T val) {
            this(val, null);
        }

        public Node(T val, Node<T> next) {
            this.val = val;
            this.next = next;
        }
    }


    // * Linked list cycle
    // * Time complexity - o(n) / o(2n)
    // * Space complexity - o(1)
    // * Based off the Floyd's cycle finding algorithm - Tortoise and Hare algorithm
    public static boolean hasCycle(Node<Integer> nodes) {
        // WRITE YOUR BRILLIANT CODE HERE
        Node fast = nodes;
        Node slow = nodes;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }
}
