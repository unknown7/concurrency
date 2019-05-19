package sharingConstrainedResources.generator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {
    private int number = 0;
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
