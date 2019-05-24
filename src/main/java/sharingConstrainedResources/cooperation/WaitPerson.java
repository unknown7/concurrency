package sharingConstrainedResources.cooperation;

public class WaitPerson implements Runnable {
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null) {
                        wait();
                    }
                }
                System.err.println("get " + restaurant.meal);
                synchronized (restaurant.chef) {
                    if (restaurant.meal != null) {
                        restaurant.meal = null;
                        restaurant.chef.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.err.println("WaitPerson interrupted");
        }
    }
}
