package sharingConstrainedResources.cooperation;

public class Toast {
    public enum Status {
        DRY, BUTTERED, JAMED
    }
    private int id;
    private Status status = Status.DRY;
    public Toast(int id) {
        this.id = id;
    }
    public void butter() {
        this.status = Status.BUTTERED;
    }
    public void jam() {
        this.status = Status.JAMED;
    }
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Toast " + id;
    }
}
