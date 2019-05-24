package sharingConstrainedResources.cooperation;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car {
    private boolean waxed = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void waxed() {
        lock.lock();
        try {
            this.waxed = true;
            System.err.println("waxed");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    public void buffed() {
        lock.lock();
        try {
            this.waxed = false;
            System.err.println("buffed");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    public void waitingForWax() throws InterruptedException {
        lock.lock();
        try {
            while (!this.waxed)
                condition.await();
        } finally {
            lock.unlock();
        }

    }
    public synchronized void waitingForBuff() throws InterruptedException {
        lock.lock();
        try {
            while (this.waxed)
                condition.await();
        } finally {
            lock.unlock();
        }
    }
}
