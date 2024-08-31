package AlgoMonsterCourse.heap;

import java.util.*;

public class Heap {
    public static void main(String[] args) {

    }

    // * Basic way of using a heap in java
    public static List<Integer> returnTopThree(List<Integer> arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.addAll(arr);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(heap.poll());
        }
        return result;
    }

    // * 973. K Closest Points to Origin
    public int[][] kClosest(int[][] points, int k) {
        Comparator<int[]> distanceComparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return distanceToOrigin(o1) - distanceToOrigin(o2);
            }

            private int distanceToOrigin(int[] p) {
                return (int) (Math.pow(p[0], 2) + Math.pow(p[1], 2));
            }
        };

        PriorityQueue<int[]> heap = new PriorityQueue<>(points.length, distanceComparator);
        heap.addAll(Arrays.asList(points));

        int[][] result = new int[k][];
        for (int i = 0; i < k; i++) {
            result[i] = heap.poll();
        }

        return result;
    }
}
