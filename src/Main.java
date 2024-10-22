import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {

        PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> {
            return Long.compare(b, a);
        });
    }

}