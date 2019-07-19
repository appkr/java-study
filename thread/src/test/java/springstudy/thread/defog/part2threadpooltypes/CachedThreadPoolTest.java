package springstudy.thread.defog.part2threadpooltypes;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import springstudy.thread.defog.part1executorservice.FixedThreadPoolTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CachedThreadPoolTest {

    @Test
    public void testCachedThreadPool() {
        ExecutorService es = Executors.newCachedThreadPool();
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
