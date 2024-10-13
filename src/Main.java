import java.util.ArrayList;
import java.util.List;

public class Main {
    private String str;

    public static void main(String[] args) {
        List<Integer> temp = new ArrayList<>();

        temp.sort((a, b) -> b - a);
    }
}