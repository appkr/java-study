package dev.appkr.reactiveweb.tobiep5;

import java.util.Observer;

@SuppressWarnings("deprecation")
public class Observable {

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
        System.out.println(arg);
      }
    };

    IntObservable io = new IntObservable();
    io.addObserver(ob);

    io.run();
  }
}
