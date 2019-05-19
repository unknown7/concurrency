package sharingConstrainedResources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int ident;
    public EvenChecker(IntGenerator generator, int ident) {
        this.generator = generator;
        this.ident = ident;
    }
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.err.println(val + " is not even!");
                generator.cancel();
            }
        }

    }
    public static void test(IntGenerator generator, int count) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            exec.execute(new EvenChecker(generator, i));
        }
        exec.shutdown();
    }
    public static void test(IntGenerator generator) {
        test(generator, 10);
    }
}
