package sharingConstrainedResources.cooperation;

import java.util.concurrent.TimeUnit;

public class WaxOff implements Runnable {
    private Car car;
    public WaxOff(Car car) {
        this.car = car;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitingForWax();
                car.buffed();
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
