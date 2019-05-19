package sharingConstrainedResources;

public abstract class IntGenerator {
    private volatile boolean cancel = false;
    public boolean isCanceled() {
        return cancel;
    }
    public void cancel() {
        this.cancel = true;
    }
    public abstract int next();
}
