package dev.appkr.reactiveweb.tobiep5;

// Duality
// Observer Pattern
// Reactive Streams

import java.util.Iterator;

public class Iterable {

  public static void main(String[] args) {
    // Iterable <-> Observable (duality)
    // Pull     <-> Push
    // 1..n까지 제공하는 데이터 클래스
    java.lang.Iterable<Integer> iter = () ->
      new Iterator<>() {
        int i = 0;
        final static int MAX = 10;
        @Override
        public boolean hasNext() {
          return i < MAX;
        }

        @Override
        public Integer next() {
          return ++i;
        }
      };

    for (Integer i : iter) {
      System.out.println(i);
    }
  }
}
