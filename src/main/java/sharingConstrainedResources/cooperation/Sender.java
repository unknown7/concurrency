package sharingConstrainedResources.cooperation;

import java.io.IOException;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sender implements Runnable {
    private PipedWriter writer = new PipedWriter();
    private Random random = new Random(47);
    @Override
    public void run() {
        try {
            for (char c = 'A'; c < 'z'; c++) {
                writer.write(c);
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            }
        } catch (IOException e) {
            System.err.println("Sender io interrupted");
        } catch (InterruptedException e) {
            System.err.println("Sender sleep interrupted");
        }
    }
    public PipedWriter getWriter() {
        return writer;
    }
}
