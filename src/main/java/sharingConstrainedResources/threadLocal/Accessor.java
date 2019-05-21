package sharingConstrainedResources.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Accessor implements Runnable {
    private int ident;
    public Accessor(Integer ident) {
        this.ident = ident;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.err.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#" + ident + ",value=" + ThreadLocalVariableHolder.getValue();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdownNow();
    }
}
