package springstudy.thread.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Semaphore;

@Slf4j
public class MutexTest {

  // Mutex is the Semaphore with an access count of 1.
  // Consider a situation of using lockers in the bank.
  // Usually the rule is that only one person is allowed to enter the locker room.
  static Semaphore semaphore = new Semaphore(1);

  @Test
  public void testMutex() {
    log.info("Total available Semaphore permites: {}", semaphore.availablePermits());
    final BankLocker a = new BankLocker("A");
    final BankLocker b = new BankLocker("B");
    final BankLocker c = new BankLocker("C");
    final BankLocker d = new BankLocker("D");
    final BankLocker e = new BankLocker("E");
    final BankLocker f = new BankLocker("F");

    try {
      a.start(); b.start(); c.start(); d.start(); e.start(); f.start();
      a.join(); b.join(); c.join(); d.join(); e.join(); f.join();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  static class BankLocker extends Thread {
    String name;

    public BankLocker(String name) {
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
