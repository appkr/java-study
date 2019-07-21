package springstudy.concurrent.part1executorservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RunnableTest {

    @Test
    public void testRunnable() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Task());
            thread.start();
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
