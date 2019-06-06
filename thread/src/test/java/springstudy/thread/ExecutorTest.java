package springstudy.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@Slf4j
public class ExecutorTest {

    private Map<String, String> map = new HashMap<>();

    @Test
    public void 메인과_다른_스레드에서_작업을_실행한다() {
        String t1 = Thread.currentThread().getName();
        log.info("t1 스레드 이름: {}", t1);
        assertEquals("main", t1);

        Executor e = Executors.newSingleThreadExecutor();

        e.execute(() -> {
            String t2 = Thread.currentThread().getName();
            log.info("t2 스레드 이름: {}", t2);
            assertNotEquals("main", t2);
        });
    }

    @Test
    public void 싱글_스레드_실행기에서는_일련의_작업을_싱글_스레드로만_처리한다() {
        log.info("Thread name of main: {}", Thread.currentThread().getName());

        Executor e = Executors.newSingleThreadExecutor();

        e.execute(() -> {
            String t1 = Thread.currentThread().getName();
            log.info("Thread name of t1: {}", t1);
            map.put("t1", t1);
        });

        e.execute(() -> {
            String t2 = Thread.currentThread().getName();
            log.info("Thread name of t2: {}", t2);
            map.put("t2", t2);
            assertEquals(map.getOrDefault("t1", null), map.getOrDefault("t2", null));
        });
    }

    @Test
    public void 싱글_스레드_실행기는_블록킹처럼_작동한다() {
        log.info("Thread name of main: {}", Thread.currentThread().getName());
        AtomicInteger counter = new AtomicInteger();

        ExecutorService e = Executors.newSingleThreadExecutor();
        e.submit(() -> {
            counter.set(1);
        });
        e.submit(() -> {
            counter.compareAndSet(1, 2);
            log.info("counter: {}", counter.get());
        });

        assertTrue(true);
    }
}
