package springstudy.thread;

public class ConsumerThread extends Thread {

  DataBox dataBox;

  public ConsumerThread(DataBox dataBox) {
    this.dataBox = dataBox;
  }

  @Override
  public void run() {
    for (int i = 0; i < 3; i++) {
      dataBox.getData();
    }
  }
}
