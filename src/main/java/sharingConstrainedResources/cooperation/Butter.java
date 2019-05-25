package sharingConstrainedResources.cooperation;

import java.util.concurrent.BlockingQueue;

public class Butter implements Runnable {
    private BlockingQueue<Toast> dryQueue, butteredQueue;
    public Butter(BlockingQueue<Toast> dryQueue, BlockingQueue<Toast> butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = dryQueue.take();
                toast.butter();
                butteredQueue.put(toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
