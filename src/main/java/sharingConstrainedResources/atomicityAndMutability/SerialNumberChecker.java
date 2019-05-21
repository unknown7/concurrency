package sharingConstrainedResources.atomicityAndMutability;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialNumberChecker implements Runnable {
    private CircularSet set = new CircularSet(1000);
    @Override
    public void run() {
        while (true) {
            int value = SerialNumberGenerator.next();
            if (set.contain(value)) {
                System.err.println("Duplicated number:" + value);
                System.exit(0);
            }
            set.add(value);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new SerialNumberChecker());
        }
    }
}
