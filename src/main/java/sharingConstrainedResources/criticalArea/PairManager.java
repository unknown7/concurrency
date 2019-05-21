package sharingConstrainedResources.criticalArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PairManager {
    private AtomicInteger counter = new AtomicInteger(0);
    private Pair pair = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
    public Pair getPair() {
        return new Pair(pair.getX(), pair.getY());
    }
    // Assume this is a time consuming operation
    public void store(Pair pair) {
        storage.add(pair);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public abstract void increment();
}
