package sharingConstrainedResources.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random random = new Random(47);
    private static int count;
    private final int id = count++;
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();
    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }
    @Override
    public int compareTo(PrioritizedTask o) {
        return priority > o.priority ? 1 : (priority < o.priority ? -1 : 0);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(this);
    }

    @Override
    public String toString() {
        return "[ + " + priority + "] Task " + id;
    }

    public String summary() {
        return "(" + id + " " + priority + ") ";
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService exec;
        public EndSentinel(ExecutorService exec) {
            super(-1);
            this.exec = exec;
        }

        @Override
        public void run() {
            for (PrioritizedTask prioritizedTask : sequence) {
                System.err.println(prioritizedTask.summary());
            }
            exec.shutdownNow();
        }
    }
}
