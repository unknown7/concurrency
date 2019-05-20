package sharingConstrainedResources.lock.generator;

public class SynchronizedEvenGenerator extends IntGenerator {
    private int number = 0;
    public synchronized int next() {
        ++number;
        Thread.yield();
        ++number;
        return number;
    }
}
