package sharingConstrainedResources.cooperation;

import java.util.concurrent.TimeUnit;

public class BusBoy implements Runnable {
    private Restaurant restaurant;
    public BusBoy(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.table == null)
                        wait();
                }
                TimeUnit.MILLISECONDS.sleep(1000);
                System.err.println("BusBoy clean " + restaurant.table);
                synchronized (restaurant.waitPerson) {
                    restaurant.table = null;
                    restaurant.waitPerson.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("BusBoy interrupted");
        }
    }
}
