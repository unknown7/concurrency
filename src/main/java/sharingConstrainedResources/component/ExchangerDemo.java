package sharingConstrainedResources.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<List<Fat>> exchanger = new Exchanger<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExchangerProducer<>(exchanger, Fat.class));
        exec.execute(new ExchangerConsumer<>(exchanger));
    }
}
class ExchangerProducer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder = new ArrayList<>();
    private Class<T> clazz;
    public ExchangerProducer(Exchanger<List<T>> exchanger, Class<T> clazz) {
        this.exchanger = exchanger;
        this.clazz = clazz;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < 5; i++) {
                    holder.add(clazz.newInstance());
                }
                holder = exchanger.exchange(holder);
                System.err.println("Producer get holder: ");
                for (T item : holder) {
                    System.err.println(item);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder = new ArrayList<>();
    public ExchangerConsumer(Exchanger<List<T>> exchanger) {
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                System.err.println("Consumer get holder: ");
                Iterator<T> iterator = holder.iterator();
                while (iterator.hasNext()) {
                    T item = iterator.next();
                    System.err.println(item);
                    iterator.remove();
                }
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
