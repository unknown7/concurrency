package sharingConstrainedResources.atomicityAndMutability;

public class CircularSet {
    private int[] array;
    private int len;
    private int index;
    public CircularSet(int len) {
        array = new int[len];
        this.len = len;
        for (int i = 0; i < len; i++) {
            array[i] = -1;
        }
    }
    public synchronized void add(int number) {
        array[index] = number;
        index = ++index % len;
    }
    public synchronized boolean contain(int number) {
        for (int i = 0; i < len; i++) {
            if (array[i] == number)
                return true;
        }
        return false;
    }
}
