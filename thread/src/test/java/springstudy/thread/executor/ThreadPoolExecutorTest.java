package springstudy.thread.executor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.*;

@Slf4j
public class ThreadPoolExecutorTest {

    @Test
    public void 설정한_개수의_스레드와_큐로_작업을_처리한다() throws InterruptedException {
        log.info("I'm in {} thread", Thread.currentThread().getName());
        ThreadPoolExecutor e = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        e.submit(() -> {
            log.info("Handling task at {}", Thread.currentThread().getName());
            Thread.sleep(1000);
            return null;
        });

        e.submit(() -> {
            log.info("Handling task at {}", Thread.currentThread().getName());
            Thread.sleep(1000);
            return null;
        });

        e.submit(() -> {
            log.info("Handling task at {}", Thread.currentThread().getName());
            Thread.sleep(1000);
            return null;
        });

        // 작업 스레드는 작업 처리 중...
        assertEquals(2, e.getPoolSize());
        assertEquals(1, e.getQueue().size());
    }
}
