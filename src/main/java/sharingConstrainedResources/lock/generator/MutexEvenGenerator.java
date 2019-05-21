package sharingConstrainedResources.lock.generator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {
    private Lock lock = new ReentrantLock();
    public int next() {
        lock.lock();
        try {
            ++number;
            Thread.yield();
            ++number;
            return number;
        } finally {
            lock.unlock();
        }
    }
}
