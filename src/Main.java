import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private String str;

    public static void main(String[] args) {
        System.out.println(markerPlaced(new int[]{-8, 0, 2, 4, 5}, new int[]{-9, 4, 5, 6, 8}));
    }

    public static int markerPlaced(int[] startX, int[] endX) {
        int answer = 0;
        // Combine into a single array and sort it
        int[][] lineSegments = new int[startX.length][];
        for (int i = 0; i < startX.length; i++) {
            lineSegments[i] = new int[]{startX[i], endX[i]};
        }
        Arrays.sort(lineSegments, Comparator.comparingInt(a -> a[0]));

        int count = 0;
        for (int i = 0; i < lineSegments.length - 1; i++) {
            if (i == 0) {
                continue;
            }
            if (lineSegments[i][1] >= lineSegments[i + 1][0]) {
                count++;
            } else {
                answer += count;
                count++;
            }
        }
        return answer + count;
    }

}