package sharingConstrainedResources.lock.generator;

public interface Generator {
    boolean isCanceled();
    void cancel();
    int next();
}
