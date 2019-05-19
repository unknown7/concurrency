package sharingConstrainedResources.generator;

public class EvenGenerator extends IntGenerator {
    private int number = 0;
    public int next() {
        ++number;
        Thread.yield();
        ++number;
        return number;
    }
}
