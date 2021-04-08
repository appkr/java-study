package springstudy.thread;

public class WaitNotifyExample {

  public static void main(String[] args) {
    DataBox dataBox = new DataBox();
    new ProducerThread(dataBox).start();
    new ConsumerThread(dataBox).start();
  }
}
