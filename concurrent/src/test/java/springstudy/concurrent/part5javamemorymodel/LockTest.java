package springstudy.concurrent.part5javamemorymodel;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void testLockVisibility() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        LockVisibility sut = new LockVisibility();

        es.submit(sut::writerThread);
        es.submit(sut::readerThread);
    }

    static class LockVisibility {
        int a = 0, b = 0, c = 0, x = 0;
        Lock lock = new ReentrantLock();

        public void writerThread() {
            lock.lock();
            a = 1;
            b = 1;
            c = 1;
            x = 1;
            lock.unlock();
        }

        public void readerThread() {
            lock.lock();
            int r2 = x;
            int d1 = a;
            int d2 = b;
            int d3 = c;
            lock.unlock();
        }
    }
}
