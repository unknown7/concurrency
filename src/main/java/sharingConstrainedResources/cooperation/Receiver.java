package sharingConstrainedResources.cooperation;

import java.io.IOException;
import java.io.PipedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Receiver implements Runnable {
    private PipedReader reader;
    public Receiver(Sender sender) {
        try {
            this.reader = new PipedReader(sender.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (true)
                System.err.println("receive " + (char) reader.read());
        } catch (IOException e) {
            System.err.println("Receiver io interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(10);
        exec.shutdownNow();
    }
}
