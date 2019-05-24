package sharingConstrainedResources.cooperation;

public class Meal {
    private final int id;
    public Meal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Meal " + id;
    }
}
