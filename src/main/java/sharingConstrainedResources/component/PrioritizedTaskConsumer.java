package sharingConstrainedResources.component;

import java.util.concurrent.PriorityBlockingQueue;

public class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> queue;
    public PrioritizedTaskConsumer(PriorityBlockingQueue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted())
                queue.take().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
