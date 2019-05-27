package sharingConstrainedResources.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Runnable, Delayed {
    private static int count;
    private final int id = count++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delta) {
        this.delta = delta;
        this.trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public void run() {
        System.err.print(this + "\t");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        int compare = trigger > that.trigger ? 1 : (trigger < that.trigger ? -1 : 0);
        return compare;
    }

    @Override
    public String toString() {
        return "[" + delta + "] Task " + id;
    }

    public String summary() {
        return "(" + id + " " + delta + ") ";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;
        public EndSentinel(int delay, ExecutorService exec) {
            super(delay);
            this.exec = exec;
        }

        @Override
        public void run() {
            for (DelayedTask delayedTask : sequence) {
                System.err.println(delayedTask.summary());
            }
            exec.shutdownNow();
        }
    }
}
