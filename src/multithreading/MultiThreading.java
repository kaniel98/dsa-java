package multithreading;

public class MultiThreading {
    public static void main(String[] args) {
        // * Using Threads, need to use .start() to execute it in a separate thread context
        UsingThreads usingThreads = new UsingThreads();
        usingThreads.start();

        // * Using Runnable
        Thread usingRunnable = new Thread(new UsingRunnable());
        usingRunnable.start();
    }

    // Region: Ways to implement multi-threading
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

    // End: Ways to implement multi threading

    // Region: Tools to help with multi threading
    /*
     * volatile ensures that all threads see the same value for the same variable
     * Prevents common issue with visibility of shared objects
     */
    public class SharedResource {
        // * Value will be consistent across threads
        private volatile int counter = 0;

        public void increment() {
            counter++;
        }

        public int getCounter() {
            return counter;
        }
    }

}
