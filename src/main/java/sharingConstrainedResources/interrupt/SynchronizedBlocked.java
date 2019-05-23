package sharingConstrainedResources.interrupt;

public class SynchronizedBlocked implements Runnable {
    public SynchronizedBlocked() {
        new Thread(() -> {
            f();
        }).start();
    }

    private synchronized void f() {
        while (true)
            Thread.yield();
    }

    @Override
    public void run() {
        try {
            f();
        } catch (Exception e) {
            System.err.println("Interrupted from Synchronized.run()");
        }
        System.err.println("Exiting Synchronized.run()");
    }
}
