package springstudy.thread;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

@Slf4j
public class GuavaThreadPoolTest {

    @Test
    public void directExecutorShouldHandlingTaskInBlockingManner() {
        Executor e = MoreExecutors.directExecutor();
        AtomicBoolean res = new AtomicBoolean();

        e.execute(() -> {
            try {
                log.info("Handling task at {}", Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            res.set(true);
        });

        log.info("Task handling delegated");
        assertTrue(res.get());
    }

    @Test
    public void exitingExecutorServiceShutdownTheThreadsWhenMainShutdown() {
        ThreadPoolExecutor e = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        ExecutorService es = MoreExecutors.getExitingExecutorService(e, 100, TimeUnit.MILLISECONDS);

        es.submit(() -> {
            log.info("Start handling task");
            while(true) {}
        });

        log.info("Task handling delegated");
    }

    @Test
    public void listenableFuture() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        ListeningExecutorService listeningEs = MoreExecutors.listeningDecorator(es);

        ListenableFuture<String> f1 = listeningEs.submit(() -> {
            log.info("Handling task at {}", Thread.currentThread().getName());
            return "Hello Thread from " + Thread.currentThread().getName();
        });

        ListenableFuture<String> f2 = listeningEs.submit(() -> {
            log.info("Handling task at {}", Thread.currentThread().getName());
            return "Hello Thread from " + Thread.currentThread().getName();
        });

        log.info("Task handling delegated");
        String res = Futures.allAsList(f1, f2).get()
            .stream()
            .collect(Collectors.joining(", "));
        log.info("Res: {}", res);

        // 16:43:37.598 [pool-1-thread-1] INFO  s.thread.GuavaThreadPoolTest - Handling task at pool-1-thread-1
        // 16:43:37.598 [main] INFO  s.thread.GuavaThreadPoolTest - Task handling delegated
        // 16:43:37.598 [pool-1-thread-2] INFO  s.thread.GuavaThreadPoolTest - Handling task at pool-1-thread-2
        // 16:43:37.626 [main] INFO  s.thread.GuavaThreadPoolTest - Res: Hello Thread from pool-1-thread-1, Hello Thread from pool-1-thread-2
    }
}
