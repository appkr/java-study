package dev.appkr.reactiveweb.tobiep5;

import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class NIObservable {

  static class IntObservable extends java.util.Observable implements Runnable {
    @Override
    public void run() {
      for (int i = 1; i <= 10; i++) {
        setChanged();
        notifyObservers(i);
      }
    }
  }

  public static void main(String[] args) {
    Observer ob = new Observer() {
      @Override
      public void update(java.util.Observable o, Object arg) {
        System.out.println(Thread.currentThread().getName() + " " + arg);
      }
    };

    IntObservable io = new IntObservable();
    io.addObserver(ob);

    ExecutorService es = Executors.newSingleThreadExecutor();
    System.out.println(Thread.currentThread().getName() + " EXIT");
    es.submit(io);
    es.shutdown();
  }
}
