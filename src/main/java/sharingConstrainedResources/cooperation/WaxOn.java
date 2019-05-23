package sharingConstrainedResources.cooperation;

import java.util.concurrent.TimeUnit;

public class WaxOn implements Runnable {
    private Car car;
    public WaxOn(Car car) {
        this.car = car;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waxed();
                car.waitingForBuff();
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
