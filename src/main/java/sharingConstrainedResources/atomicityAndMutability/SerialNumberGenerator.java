package sharingConstrainedResources.atomicityAndMutability;

public class SerialNumberGenerator {
    private static volatile int number = 0;
    public static int next() {
        return number++;
    }
}
