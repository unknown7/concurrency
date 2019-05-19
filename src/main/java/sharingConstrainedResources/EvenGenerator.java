package sharingConstrainedResources;

public class EvenGenerator extends IntGenerator {
    private int number = 0;
    public int next() {
        ++number;
        Thread.yield();
        ++number;
        return number;
    }

    public static void main(String[] args) {
        EvenGenerator generator = new EvenGenerator();
        EvenChecker.test(generator);
    }
}
