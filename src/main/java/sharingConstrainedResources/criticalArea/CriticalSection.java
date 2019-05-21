package sharingConstrainedResources.criticalArea;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CriticalSection {
    public static void main(String[] args) {
        PairManager manager1 = new PairManager1(),
                    manager2 = new PairManager2();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new PairManipulator(manager1));
        exec.execute(new PairManipulator(manager2));
        exec.execute(new PairChecker(manager1));
        exec.execute(new PairChecker(manager2));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("manager1:" + manager1);
                System.err.println("manager2:" + manager2);
                System.exit(0);
            }
        }, 3000);

    }
}
