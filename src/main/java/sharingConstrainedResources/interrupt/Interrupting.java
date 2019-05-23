package sharingConstrainedResources.interrupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Interrupting {
    public static void test(Runnable r) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<?> f = exec.submit(r);
        TimeUnit.SECONDS.sleep(1);
        f.cancel(true);
//        exec.shutdownNow();
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.err.println("Aborting");
        System.exit(0);
    }
}
