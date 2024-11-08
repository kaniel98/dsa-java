package multithreading;

public class SynchronisedExample {
    private static int counter = 0;

    // Does the same thing as AtomicInteger.incrementAndGet()
    // This enforces a lock on the method, so only one thread can access it at a time
    public static synchronized void incrementCounter() {
        counter++;
    }

    public static void main(String[] args) {
        // Create multiple threads to increment the counter concurrently
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                incrementCounter(); // Atomically increment by 1
//it guarantees that no other thread can interrupt or interfere with the increment operation while it is in progress.
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                incrementCounter(); // Atomically increment by 1
//it guarantees that no other thread can interrupt or interfere with the increment operation while it is in progress.
            }
        });

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final counter value
        System.out.println("Final Counter Value: " + counter);
    }
}
