package sharingConstrainedResources.component;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PrioritizedTaskProducer implements Runnable{
    private Random random = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;
    public PrioritizedTaskProducer(Queue queue, ExecutorService exec) {
        this.queue = queue;
        this.exec = exec;
    }
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            queue.add(new PrioritizedTask(i));
        }
        queue.add(new PrioritizedTask.EndSentinel(exec));
    }
}
