package sharingConstrainedResources.cooperation;

import java.util.concurrent.BlockingQueue;

public class Jam implements Runnable {
    private BlockingQueue<Toast> butteredQueue, finishedQueue;
    public Jam(BlockingQueue<Toast> butteredQueue, BlockingQueue<Toast> finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = butteredQueue.take();
                toast.jam();
                finishedQueue.put(toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
