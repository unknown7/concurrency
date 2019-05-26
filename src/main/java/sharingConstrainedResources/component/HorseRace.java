package sharingConstrainedResources.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HorseRace {
    public static void main(String[] args) {
        final int FINISH_LINE = 27;
        final int HORSE_NUM = 9;
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Horse> horses = new ArrayList<>();
        CyclicBarrier barrier = new CyclicBarrier(HORSE_NUM, () -> {
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < FINISH_LINE; i++) {
                buff.append("=");
            }
            System.err.println(buff);
            boolean stop = false;
            Horse winner = null;
            for (Horse horse : horses) {
                int strides = horse.getStrides();
                String track = horse.track();
                System.err.println(track);
                if (strides >= FINISH_LINE) {
                    stop = true;
                    winner = horse;
                }
            }
            if (stop) {
                exec.shutdownNow();
                System.err.println("Winner is: " + winner.toString());
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < HORSE_NUM; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }
}
