package sharingConstrainedResources.component;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
    private CyclicBarrier barrier;
    private static int count = 0;
    private int id = count++;
    private int strides = 0;
    private static Random random = new Random(47);
    public Horse(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                barrier.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized int getStrides() {
        return strides;
    }
    public String track() {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < strides; i++) {
            buff.append("*");
        }
        buff.append(id);
        return buff.toString();
    }

    @Override
    public String toString() {
        return "Horse " + id;
    }
}
