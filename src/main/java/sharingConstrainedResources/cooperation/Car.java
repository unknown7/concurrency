package sharingConstrainedResources.cooperation;

public class Car {
    private boolean waxed = false;
    public synchronized void waxed() {
        this.waxed = true;
        System.err.println("waxed");
        notifyAll();
    }
    public synchronized void buffed() {
        this.waxed = false;
        System.err.println("buffed");
        notifyAll();
    }
    public synchronized void waitingForWax() throws InterruptedException {
        while (!this.waxed)
            wait();
    }
    public synchronized void waitingForBuff() throws InterruptedException {
        while (this.waxed)
            wait();
    }
}
