package skillter.immersivedamage.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {

    private static AtomicInteger threadCounter = new AtomicInteger(0);

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(20, r -> new Thread(r, "Thread " + threadCounter.incrementAndGet()));


    public static ScheduledFuture<?> schedule(Runnable r, long delay, TimeUnit unit) {
        return executor.schedule(r, delay, unit);
    }

    public static ScheduledFuture<?> schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return executor.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public static void runAsync(Runnable r) {
        executor.execute(r);
    }

}
