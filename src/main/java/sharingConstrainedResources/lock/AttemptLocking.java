package sharingConstrainedResources.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
    private Lock lock = new ReentrantLock();
    public void untimed() {
        boolean captured = lock.tryLock();
        System.err.println("untimed captured:" + captured);
        if (captured)
            lock.unlock();
    }
    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
            System.err.println("timed captured:" + captured);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed();
        Thread daemon = new Thread() {
            {
                setDaemon(true);
            }
            @Override
            public void run() {
                al.lock.lock();
                System.err.println("acquired");
            }
        };
        daemon.start();
        daemon.join();
        al.untimed();
        al.timed();
    }
}
