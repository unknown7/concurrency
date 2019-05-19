package sharingConstrainedResources.generator;

public interface Generator {
    boolean isCanceled();
    void cancel();
    int next();
}
