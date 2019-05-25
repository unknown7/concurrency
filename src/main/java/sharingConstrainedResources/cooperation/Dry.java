package sharingConstrainedResources.cooperation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Dry implements Runnable {
    private BlockingQueue<Toast> dry;
    private int count = 0;
    public Dry(BlockingQueue dry) {
        this.dry = dry;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = new Toast(count++);
                dry.put(toast);
                TimeUnit.MILLISECONDS.sleep(900);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
