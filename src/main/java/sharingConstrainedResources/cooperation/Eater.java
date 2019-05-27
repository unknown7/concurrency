package sharingConstrainedResources.cooperation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Eater implements Runnable {
    private BlockingQueue<Toast> finishedQueue;
    public Eater(BlockingQueue<Toast> finishedQueue) {
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = finishedQueue.take();
                System.err.println("Chomp " + toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Toast> dryQueue = new LinkedBlockingDeque<>(),
                             butteredQueue = new LinkedBlockingDeque<>(),
                             finishedQueue = new LinkedBlockingDeque<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Dry(dryQueue));
        exec.execute(new Butter(dryQueue, butteredQueue));
        exec.execute(new Jam(butteredQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
    }
}
