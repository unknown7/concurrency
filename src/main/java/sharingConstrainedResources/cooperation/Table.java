package sharingConstrainedResources.cooperation;

public class Table {
    private int id;
    public Table(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Table " + id;
    }
}
