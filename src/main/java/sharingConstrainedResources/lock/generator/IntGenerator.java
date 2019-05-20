package sharingConstrainedResources.lock.generator;

public abstract class IntGenerator implements Generator {
    private volatile boolean cancel = false;
    public boolean isCanceled() {
        return cancel;
    }
    public void cancel() {
        this.cancel = true;
    }
    public abstract int next();
}
