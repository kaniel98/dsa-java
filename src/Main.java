import java.util.Arrays;

public class Main {
    private String str;

    public static void main(String[] args) {
        int[] temp = new int[]{};
        System.out.println(Arrays.toString(temp));
        int[] copy = Arrays.copyOf(temp, 3);
        System.out.println(Arrays.toString(copy));

    }
}