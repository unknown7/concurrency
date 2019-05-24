package sharingConstrainedResources.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {
    public Meal meal;
    public Table table;
    public WaitPerson waitPerson = new WaitPerson(this);
    public Chef chef = new Chef(this);
    public BusBoy busBoy = new BusBoy(this);
    public ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant() {
        exec.execute(waitPerson);
        exec.execute(chef);
        exec.execute(busBoy);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}
