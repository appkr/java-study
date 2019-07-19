package springstudy.thread.defog.part4callableNfuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class CallableTest {

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        List<Future<Integer>> allFutures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Future<Integer> future = es.submit(new Task());
            allFutures.add(future);
        }
        log.info("Main thread done!");

        allFutures.forEach(f -> {
            try {
                log.info("isCanceled: {}, isDone: {}", f.isCancelled(), f.isDone());
                Integer res = f.get(1, TimeUnit.SECONDS); // blocking
                log.info("result: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                log.info("Interrupted");
            } catch (TimeoutException e) {
                log.info("Couldn't complete task before timeout");
            }
        });
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3_000);
            log.info("Working in worker thread");
            return new Random().nextInt();
        }
    }
}
