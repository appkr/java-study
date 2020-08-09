package springstudy.thread.mutex;

public class SequenceGenerator {

  private int currentValue = 0;

  public int getNextSequence() {
    currentValue += 1;
    return currentValue;
  }
}
