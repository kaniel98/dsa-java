import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.stream(new int[]{1, 2, 3, 4}).reduce(
                1, (a, b) -> a * b));
    }

    int product = Arrays.stream(new int[]{}).reduce(1, (a, b) -> a * b);

}