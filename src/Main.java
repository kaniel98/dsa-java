import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Main {
    private String str;

    public static void main(String[] args) {
        List<String> str = new ArrayList<>(new HashSet<>());
        Collections.sort(str);
    }
}