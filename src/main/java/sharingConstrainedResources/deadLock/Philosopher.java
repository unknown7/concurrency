package sharingConstrainedResources.deadLock;

public class Philosopher implements Runnable {
    private int id;
    private ChopStick left;
    private ChopStick right;
    public Philosopher(int id, ChopStick left, ChopStick right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            left.take();
            System.err.println(this + " taken left");
            right.take();
            System.err.println(this + " taken right");
            left.drop();
            right.drop();
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}
