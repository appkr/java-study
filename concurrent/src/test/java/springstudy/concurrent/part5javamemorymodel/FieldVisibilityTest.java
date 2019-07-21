package springstudy.concurrent.part5javamemorymodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FieldVisibilityTest {

    @Test
    public void testFieldVisibilityFail() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        FieldVisibility sut = new FieldVisibility();

        es.execute(() -> {
            sut.writerThread();
        });

        es.execute(() -> {
            sut.readerThread();
            log.info("Value in readerThread: {}", sut.getR2());
        });

        Thread.sleep(100);
    }

    static class FieldVisibility {
        int x = 0;
        int r2;

        public void writerThread() {
            x = 1;
        }

        public void readerThread() {
            r2 = x;
        }

        public int getR2() {
            return r2;
        }
    }
}
