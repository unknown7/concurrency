package sharingConstrainedResources.criticalArea;

public class PairChecker implements Runnable {
    private PairManager manager;
    public PairChecker(PairManager manager) {
        this.manager = manager;
    }
    @Override
    public void run() {
        while (true) {
            manager.counter.incrementAndGet();
            manager.getPair().checkStatus();
        }
    }
}
