package springstudy.thread.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreTest {

  // Consider an ATM cubicle with 4 ATMs,
  // Semaphore can make sure only 4 people can access simultaneously.
  static Semaphore semaphore = new Semaphore(4);

  @Test
  public void testSemaphore() {
    log.info("Total available Semaphore permites: {}", semaphore.availablePermits());
    final ATMConsole a = new ATMConsole("A");
    final ATMConsole b = new ATMConsole("B");
    final ATMConsole c = new ATMConsole("C");
    final ATMConsole d = new ATMConsole("D");
    final ATMConsole e = new ATMConsole("E");
    final ATMConsole f = new ATMConsole("F");

    try {
      a.start(); b.start(); c.start(); d.start(); e.start(); f.start();
      a.join(); b.join(); c.join(); d.join(); e.join(); f.join();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  static class ATMConsole extends Thread {
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
