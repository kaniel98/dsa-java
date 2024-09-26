import java.util.ArrayList;
import java.util.List;

public class Main {
    private String str;

    public static void main(String[] args) {
        List<Integer> result = new ArrayList<>(List.of(1, 2, 3));
        int[] target = result.stream().mapToInt(i -> i).toArray();
    }
}