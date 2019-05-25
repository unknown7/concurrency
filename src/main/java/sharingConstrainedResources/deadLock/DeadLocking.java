package sharingConstrainedResources.deadLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLocking {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        int size = 5;
        ChopStick[] chopSticks = new ChopStick[size];
        for (int i = 0; i < size; i++) {
            chopSticks[i] = new ChopStick();
        }
        for (int i = 0; i < size; i++) {
            exec.execute(new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % size]));
        }
    }
}
