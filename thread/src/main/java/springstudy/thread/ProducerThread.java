package springstudy.thread;

public class ProducerThread extends Thread {

  DataBox dataBox;

  public ProducerThread(DataBox dataBox) {
    this.dataBox = dataBox;
  }

  @Override
  public void run() {
    for (int i = 0; i < 3; i++) {
      String data = "Data-" + i;
      dataBox.setData(data);
    }
  }
}
