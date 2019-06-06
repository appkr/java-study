package springstudy.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

import static junit.framework.TestCase.assertTrue;

@Slf4j
public class ScheduledThreadPoolExecutorTest {

    @Test
    public void 설정한_미래_시점에_작업을_처리한다() throws InterruptedException {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(5);
        es.schedule(() -> {
            log.info("Hello Thread from {}", Thread.currentThread().getName());
        }, 500, TimeUnit.MILLISECONDS);

        log.info("Task delegated to thread and waiting for the result");

        Thread.sleep(1000);
        assertTrue(true);
    }

    @Test
    public void 설정한_미래_시점에_작업을_시작해서_주기적으로_처리한다() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService es = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = es.scheduleAtFixedRate(() -> {
            log.info("Hello Thread from {}", Thread.currentThread().getName());
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        log.info("Task delegated to thread and waiting for the result");
        lock.await(1000, TimeUnit.MILLISECONDS);
        future.cancel(true);

        // 15:25:59.290 [main] INFO  s.t.ScheduledThreadPoolExecutorTest - Task delegated to thread and waiting for the result
        // 15:25:59.789 [pool-1-thread-1] INFO  s.t.ScheduledThreadPoolExecutorTest - Hello Thread from pool-1-thread-1
        // 15:25:59.891 [pool-1-thread-1] INFO  s.t.ScheduledThreadPoolExecutorTest - Hello Thread from pool-1-thread-1
        // 15:25:59.989 [pool-1-thread-2] INFO  s.t.ScheduledThreadPoolExecutorTest - Hello Thread from pool-1-thread-2
    }
}
