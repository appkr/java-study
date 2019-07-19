package springstudy.thread.defog.part1executorservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FixedThreadPoolTest {

    @Test
    public void testFixedThreadPool() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        log.info("This machine has {} cpus", coreCount);

        ExecutorService es = Executors.newFixedThreadPool(coreCount);
        for (int i = 0; i < 100; i++) {
            es.execute(new Task());
        }
        log.info("Main thread done!");
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            log.info("Working in worker thread");
        }
    }
}
