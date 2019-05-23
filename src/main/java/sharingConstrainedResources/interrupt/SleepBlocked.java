package sharingConstrainedResources.interrupt;

import java.util.concurrent.TimeUnit;

public class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }
        System.err.println("Exiting SleepBlocked.run()");
    }
}
