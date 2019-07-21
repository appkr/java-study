package springstudy.concurrent.part2threadpooltypes;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadPoolTest {

    @Test
    public void testScheduledThreadPool() throws InterruptedException {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(10);

        es.schedule(new Task(), 10, TimeUnit.SECONDS);
        es.scheduleAtFixedRate(new Task(), 15, 10, TimeUnit.SECONDS);
        es.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);

        log.info("Main thread done!");
        Thread.sleep(60 * 1000);
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            log.info("Working in worker thread");
        }
    }
}
