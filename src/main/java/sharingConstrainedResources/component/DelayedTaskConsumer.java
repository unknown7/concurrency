package sharingConstrainedResources.component;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> queue;
    public DelayedTaskConsumer(DelayQueue queue) {
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

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Random random = new Random(47);
        DelayQueue<DelayedTask> queue = new DelayQueue();
        for (int i = 0; i < 5; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }
}
