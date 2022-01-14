package dev.appkr.rs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class App {

    public static void main(String[] args) {
        Publisher sensor = new TempSensor();
        Subscriber display = new ScreenDisplay();
        sensor.subscribe(display);
    }

    static class TempSensor implements Publisher<Integer> {
        Iterable<Integer> its = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        public void subscribe(Subscriber<? super Integer> subscriber) {
            System.out.println("디스플레이: 온도 정보 구독 신청");
            System.out.println("온도센서: 구독 등록 중");
            final Subscription subscription = new TemperatureSubscription(subscriber, its);
            System.out.println("온도센서: 온도 정보 준비 완료");
            subscriber.onSubscribe(subscription);
        }
    }

    static class ScreenDisplay implements Subscriber<Integer> {
        Subscription subscription;
        int bufferSize = 3;

        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            System.out.println("디스플레이: 온도 정보 최초 수신");
            subscription.request(bufferSize);
        }

        public void onNext(Integer temperature) {
            System.out.println("디스플레이: onNext 온도 정보 수신 " + temperature);
            bufferSize--;
            if (bufferSize == 0) {
                System.out.println("---");
                bufferSize = 3;
                subscription.request(bufferSize);
            }
        }

        public void onError(Throwable throwable) {
            System.out.println("온도센서: 온도 정보 전송중 에러!");
        }

        public void onComplete() {
            System.out.println("온도센서: 온도 정보 전송 완료!");
        }
    }

    static class TemperatureSubscription implements Subscription {
        Subscriber<? super Integer> subscriber;
        Iterator<Integer> it;

        public TemperatureSubscription(Subscriber<? super Integer> subscriber, Iterable<Integer> its) {
            this.subscriber = subscriber;
            this.it = its.iterator();
        }

        public void request(long n) {
            while(n > 0) {
                if (it.hasNext()) {
                    subscriber.onNext(it.next());
                } else {
                    subscriber.onComplete();
                    break;
                }
                n--;
            }
        }

        public void cancel() {

        }
    }
}
