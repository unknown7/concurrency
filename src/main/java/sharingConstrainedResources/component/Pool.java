package sharingConstrainedResources.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pool<T> {
    private Semaphore semaphore;
    private final int size;
    private List<T> holder;
    private boolean[] checked;
    public Pool(Semaphore semaphore, int size, Class<T> clazz) {
        this.semaphore = semaphore;
        System.err.println("creating Pool, semaphore.length=" + semaphore.getQueueLength());
        this.size = size;
        holder = new ArrayList<T>(size);
        checked = new boolean[size];
        for (int i = 0; i < size; i++) {
            try {
                holder.add(clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public T checkOut() {
        T item = null;
        try {
            semaphore.acquire();
            item = getItem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return item;
    }
    public void checkIn(T x) {
        if (release(x))
            semaphore.release();
    }
    private synchronized T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checked[i]) {
                checked[i] = true;
                return holder.get(i);
            }
        }
        return null;
    }
    private synchronized boolean release(T x) {
        int index = holder.indexOf(x);
        if (index < 0) return false;
        if (checked[index]) {
            checked[index] = false;
            return true;
        }
        return false;
    }
}
