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
                System.err.println("WaitPerson got " + restaurant.meal);
                Meal meal;
                synchronized (restaurant.chef) {
                    meal = restaurant.meal;
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
                synchronized (this) {
                    while (restaurant.table != null) {
                        wait();
                    }
                }
                System.err.println("Table got " + meal);
                synchronized (restaurant.busBoy) {
                    restaurant.table = new Table(meal.getId());
                    restaurant.busBoy.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("WaitPerson interrupted");
        }
    }
}
