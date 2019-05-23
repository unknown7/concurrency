package sharingConstrainedResources.interrupt;

import java.io.IOException;
import java.io.InputStream;

public class IOBlocked implements Runnable {
    private InputStream is;
    public IOBlocked(InputStream is) {
        this.is = is;
    }
    @Override
    public void run() {
        try {
            is.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted())
                System.err.println("Interrupted from block I/O");
            else
                throw new RuntimeException();
        }
        System.err.println("Exiting IOBlocked.run()");
    }
}
