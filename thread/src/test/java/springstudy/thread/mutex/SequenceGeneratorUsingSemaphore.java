package springstudy.thread.mutex;

import java.util.concurrent.Semaphore;

public class SequenceGeneratorUsingSemaphore extends SequenceGenerator {

  private Semaphore mutex = new Semaphore(1);

  @Override
  public int getNextSequence() {
    try {
      mutex.acquire();
      return super.getNextSequence();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      mutex.release();
    }
    return 0;
  }
}
