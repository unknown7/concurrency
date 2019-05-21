package sharingConstrainedResources.criticalArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PairManager {
    public AtomicInteger counter = new AtomicInteger(0);
    protected Pair pair = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
    public synchronized Pair getPair() {
        return new Pair(pair.getX(), pair.getY());
    }
    // Assume this is a time consuming operation
    public void store(Pair pair) {
        storage.add(pair);
        try {
            TimeUnit.MICROSECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public abstract void increment();

    @Override
    public String toString() {
        return "x=" + pair.getX() + ",y=" + pair.getY() + ",counter=" + counter.get();
    }
}
