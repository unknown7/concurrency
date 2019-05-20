package sharingConstrainedResources.atomicityAndMutability;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable {
    private int i = 0;
    public int getValue() {
        return i;
    }
    public synchronized void evenIncrement() {
        i++;
        Thread.yield();
        i++;
    }
    @Override
    public void run() {
        while (true)
            evenIncrement();
    }

    public static void main(String[] args) throws Exception {
        AtomicityTest at = new AtomicityTest();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(at);
//        TimeUnit.SECONDS.sleep(1);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                System.err.println(value + " is not even!");
                System.exit(0);
            }
        }
    }
}
