package dev.appkr.webmvc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scratchpad {

  static ExecutorService es = Executors.newFixedThreadPool(100);
  static AtomicInteger counter = new AtomicInteger(0);

  @SneakyThrows
  void doSomething(int idx) {
    Thread.sleep(10000);
    log.info(idx + " doSomething");
  }

  @SneakyThrows
  public static void main(String[] args) {
    final Scratchpad scratchpad = new Scratchpad();

    log.info("1 start");

    // Blocking & Sync
    // Blocking: 코드 실행에 대한 제어권이 doSomething으로 넘어감
    //         : main thread는 Waiting 상태에 빠짐; 컴퓨팅 리소스는 놀고 있음
    //         : Process Life Cycle @see https://www.baeldung.com/wp-content/uploads/sites/4/2021/01/5-3.png
    // Sync    : 코드 실행에 대한 결과를 동기적으로 받음
//    scratchpad.doSomething(2);

    // Non-blocking & Async
    // Non-blocking: 코드 실행에 대한 제어권을 넘기지 않음
    // Async       : 코드 실행에 대한 결과를 언제 받을 지 알 수 없음
//    es.submit(() -> scratchpad.doSomething(2));

    // 기존의 HTTP 요청 처리 모델: per-thread 모델 (c.f. multi-process 모델 e.g. Apache mod_php 모듈)
    // Thread 개수를 무한정으로 늘리면 되지 않는가?
    // @see https://kajabi-storefronts-production.kajabi-cdn.com/kajabi-storefronts-production/blogs/8922/images/ARgLC9TQ4iVe1tHiW7vk_Context_Switching_.png
//    for (int i = 0; i < 100; i++) {
//      es.submit(() -> {
//        int idx = counter.addAndGet(1);
//        scratchpad.doSomething(idx);
//        return;
//      });
//    }

    log.info("3 end");

    es.shutdown();
    es.awaitTermination(1, TimeUnit.HOURS);
  }
}
