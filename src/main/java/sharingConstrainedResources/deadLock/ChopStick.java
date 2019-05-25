package sharingConstrainedResources.deadLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private boolean taken = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void take() {
        lock.lock();
        try {
            while (taken)
                condition.await();
            taken = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void drop() {
        lock.lock();
        try {
            taken = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
