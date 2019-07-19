package springstudy.thread.defog.part2threadpooltypes;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SingleThreadExecutorTest {

    @Test
    public void test() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
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
