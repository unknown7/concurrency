package sharingConstrainedResources.component;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TaskPortion implements Runnable {
    private CountDownLatch latch;
    private static Random random = new Random(47);
    private static int count = 0;
    private int id;
    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
        this.id = this.count++;
    }
    @Override
    public void run() {
        try {
            System.err.println(this + " work start");
            work(id);
            System.err.println(this + " work stop");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void work(int id) throws InterruptedException {
        int duration = random.nextInt(2000);
        if (id == 3) {
            duration = 7777;
        }
        TimeUnit.MILLISECONDS.sleep(duration);
    }

    @Override
    public String toString() {
        return "TaskPortion " + id;
    }
}
