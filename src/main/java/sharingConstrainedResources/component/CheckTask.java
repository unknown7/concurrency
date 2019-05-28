package sharingConstrainedResources.component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CheckTask<T> implements Runnable {
    private static int count;
    private final int id = count++;
    private Pool<T> pool;
    public CheckTask(Pool pool) {
        this.pool = pool;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.err.println(this + " checking out");
                T item = pool.checkOut();
                System.err.println(this + " checked out " + item);
                TimeUnit.SECONDS.sleep(1);
                System.err.println(this + " checking in " + item);
                pool.checkIn(item);
                System.err.println(this + " checked in " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CheckTask " + id;
    }

    public static void main(String[] args) {
        final int SIZE = 5;
        ExecutorService exec = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(SIZE);
        Pool<Fat> pool = new Pool<>(semaphore, SIZE, Fat.class);
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckTask<Fat>(pool));
        }
    }
}
