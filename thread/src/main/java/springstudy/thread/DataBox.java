package springstudy.thread;

public class DataBox {

  private String data;

  public synchronized String getData() {
    if (data == null) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }

    String returnValue = data;
    data = null;
    System.out.println("Consumer Thread가 읽은 데이터: " + returnValue);
    notify();

    return returnValue;
  }

  public synchronized void setData(String data) {
    if (this.data != null) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }

    this.data = data;
    System.out.println("Producer Thread에서 생성한 데이터: " + data);
    notify();
  }
}
