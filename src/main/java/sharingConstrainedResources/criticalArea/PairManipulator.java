package sharingConstrainedResources.criticalArea;

public class PairManipulator implements Runnable {
    private PairManager manager;
    public PairManipulator(PairManager manager) {
        this.manager = manager;
    }
    @Override
    public void run() {
        while (true)
            manager.increment();
    }
}
