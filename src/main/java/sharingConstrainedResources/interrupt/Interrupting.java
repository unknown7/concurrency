package sharingConstrainedResources.interrupt;

import java.util.concurrent.*;

public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    public static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.SECONDS.sleep(1);
        f.cancel(true);
//        exec.shutdownNow();
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        test(new Runnable() {
            @Override
            public void run() {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    System.err.println("interrupting from blocking queue!");
                }
            }
        });
        TimeUnit.SECONDS.sleep(3);
        System.err.println("Aborting");
        System.exit(0);
    }
}
