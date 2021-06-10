package dev.appkr.reactiveweb.tobiep5;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class PubSub {

  public static void main(String[] args) {
    // Publisher <- Observable
    // Subscriber <- Observer
    final java.lang.Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

    Publisher p = new Publisher() {
      @Override
      public void subscribe(Subscriber subscriber) {
        // 누구에게 데이터를 줘야할 지 등록해야 함
        Iterator<Integer> itr = iter.iterator();

        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            try {
              while(n-- > 0) {
                if (itr.hasNext()) {
                  subscriber.onNext(itr.next());
                } else {
                  subscriber.onComplete();
                  break;
                }
              }
            } catch (RuntimeException e) {
              subscriber.onError(e);
            }
          }

          @Override
          public void cancel() {

          }
        });
      }
    };

    Subscriber<Integer> s = new Subscriber<Integer>() {
      Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        System.out.println("onSubscribe");
        this.subscription.request(1);
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("onNext " + item);
        this.subscription.request(1);
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("onError");
      }

      @Override
      public void onComplete() {
        System.out.println("onComplete");
      }
    };

    p.subscribe(s);
  }
}
