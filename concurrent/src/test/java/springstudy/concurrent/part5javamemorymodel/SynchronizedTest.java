package springstudy.concurrent.part5javamemorymodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedTest {

    @Test
    public void testSynchronized() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        SynchronizedFieldVisibility sut = new SynchronizedFieldVisibility();

        es.submit(() -> {
            sut.writerThread();
        });

        es.submit(() -> {
            sut.readerThread();
        });
    }

    static class SynchronizedFieldVisibility {

        int a = 0, b = 0, c = 0;
        int x = 0;

        public void writerThread() {
            a = 1;
            b = 1;
            c = 1;

            synchronized (this) {
                // a, b, c and x are flushed into the memory
                x = 1;
            }
        }

        public void readerThread() {
            synchronized (this) {
                // upon synchronization of x, a b and c also are to be synchronized
                int r2 = x;
            }

            int d1 = a;
            int d2 = b;
            int d3 = c;
        }
    }
}
