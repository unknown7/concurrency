package sharingConstrainedResources.component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitingTask implements Runnable {
    private CountDownLatch latch;
    private static int count = 0;
    private int id;
    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
        this.id = this.count++;
    }
    @Override
    public void run() {
        try {
            latch.await();
            System.err.println(this + " wait stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "WaitingTask " + id;
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(new TaskPortion(latch));
        }
        for (int i = 0; i < 2; i++) {
            exec.execute(new WaitingTask(latch));
        }

        exec.shutdown();
    }
}
