package springstudy.thread.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

@Slf4j
public class SemaphoreRunnableTest {

  // Consider an ATM cubicle with 4 ATMs,
  // Semaphore can make sure only 4 people can access simultaneously.
  static Semaphore semaphore = new Semaphore(4);

  @Test
  public void testSemaphore() {
    log.info("Total available Semaphore permites: {}", semaphore.availablePermits());
    final ExecutorService es = Executors.newFixedThreadPool(10);
    try {
      final Future<?> a = es.submit(new ATMConsole("A"));
      final Future<?> b = es.submit(new ATMConsole("B"));
      final Future<?> c = es.submit(new ATMConsole("C"));
      final Future<?> d = es.submit(new ATMConsole("D"));
      final Future<?> e = es.submit(new ATMConsole("E"));
      final Future<?> f = es.submit(new ATMConsole("F"));

      a.get(); b.get(); c.get(); d.get(); e.get(); f.get();
    } catch (InterruptedException|ExecutionException ex) {
      ex.printStackTrace();
    }
  }

  static class ATMConsole implements Runnable {
    String name;

    public ATMConsole(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        log.info("{} : acquiring lock", name);
        log.info("{} : available Semaphore permits now: {}", name, semaphore.availablePermits());
        semaphore.acquire();
        log.info("{} : got the permit!!", name);

        try {
          for (int i = 1; i < 5; i++) {
            log.info("{} : is performing operation {}, available Semaphore permits: {}", name, i, semaphore.availablePermits());
            Thread.sleep(1000);
          }
        } finally {
          // calling release() after a successful acquire()
          log.info("{} : releasing lock...", name);
          semaphore.release();
          log.info("{} : available Semaphore permits now: {}", name, semaphore.availablePermits());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
