package sharingConstrainedResources.component;

import java.util.concurrent.TimeUnit;

public class Fat {
    private static int count;
    private final int id = count++;
    public Fat() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Fat " + id;
    }
}
