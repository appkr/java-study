package springstudy.thread.defog.part3constructorNlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolLifecycleTest {

    @Test
    public void testThreadPoolLifeCycle() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            es.execute(new Task());
        }

        log.info("Main thread done!");

        // initiate shutdown
        es.shutdown();

        // will throw RejectionExecutionException
//        es.execute(new Task());

        // will return true, since shutdown has begun
        boolean isShutdown = es.isShutdown();
        log.info("isShutdown: {}", isShutdown);

        // will return true if all tasks are completed, including queued ones
        boolean isTerminated = es.isTerminated();
        log.info("isTerminated: {}", isTerminated);

        // block until all tasks are completed or if timeout occurs
        es.awaitTermination(10, TimeUnit.SECONDS);
        log.info("isTerminated: {}", es.isTerminated());

        List<Runnable> runnables = es.shutdownNow();
        log.info("runnables {}", runnables);
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            log.info("Working in worker thread");
        }
    }
}
