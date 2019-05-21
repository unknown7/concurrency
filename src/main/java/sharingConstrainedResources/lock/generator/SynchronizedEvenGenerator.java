package sharingConstrainedResources.lock.generator;

public class SynchronizedEvenGenerator extends IntGenerator {
    public synchronized int next() {
        ++number;
        Thread.yield();
        ++number;
        return number;
    }
}
