package CommonImplementations;

public class MultiThreading {
    public static void main(String[] args) {
        // * Using Threads, need to use .start() to execute it in a separate thread context
        UsingThreads usingThreads = new UsingThreads();
        usingThreads.start();

        // * Using Runnable
        Thread usingRunnable = new Thread(new UsingRunnable());
        usingRunnable.start();
    }

    // * Only allowed to inherit the Thread class
    static class UsingThreads extends Thread {
        public void run() {
            try {
                // Displaying the thread that is running
                System.out.println(
                        "Thread " + Thread.currentThread().getId()
                                + " is running");
            } catch (Exception e) {
                // Throwing an exception
                System.out.println("Exception is caught");
            }
        }
    }

    // * More flexible - Allows inheritance of other classes
    static class UsingRunnable implements Runnable {
        public void run() {
            try {
                // Displaying the thread that is running
                System.out.println(
                        "Thread " + Thread.currentThread().getId()
                                + " is running");
            } catch (Exception e) {
                // Throwing an exception
                System.out.println("Exception is caught");
            }
        }
    }
}