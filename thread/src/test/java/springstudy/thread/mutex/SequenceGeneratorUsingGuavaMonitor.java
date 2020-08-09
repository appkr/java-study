package springstudy.thread.mutex;

import com.google.common.util.concurrent.Monitor;

public class SequenceGeneratorUsingGuavaMonitor extends SequenceGenerator {

  private Monitor mutext = new Monitor();

  @Override
  public int getNextSequence() {
    mutext.enter();
    try {
      return super.getNextSequence();
    } finally {
      mutext.leave();
    }
  }
}
