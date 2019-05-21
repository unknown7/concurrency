package sharingConstrainedResources.lock.generator;

public class EvenGenerator extends IntGenerator {
    public int next() {
        ++number;
        Thread.yield();
        ++number;
        return number;
    }
}
