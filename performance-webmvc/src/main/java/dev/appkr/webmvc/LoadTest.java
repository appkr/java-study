package dev.appkr.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {

  static AtomicInteger counter = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
    final CyclicBarrier barrier = new CyclicBarrier(101);
    final ExecutorService es = Executors.newFixedThreadPool(100);
    final RestTemplate restTemplate = new RestTemplate();
    final String url = "http://localhost:8080/rest?idx={idx}";

    for (int i = 0; i < 100; i++) {
      final Future<String> future = es.submit(() -> {
        int idx = counter.addAndGet(1);
        log.info("Thread ready: {}", idx);

        try {
          barrier.await();
        } catch (Exception e) {
        }
        final StopWatch sw = new StopWatch();
        sw.start();

        final String response = restTemplate.getForObject(url, String.class, String.valueOf(idx));
        sw.stop();
        log.info("Response: {}, Elapsed: {}", response, sw.getTotalTimeSeconds());

        return response;
      });
    }

    barrier.await();
    final StopWatch main = new StopWatch();
    main.start();

    es.shutdown();
    es.awaitTermination(1000, TimeUnit.SECONDS);

    main.stop();
    log.info("Total: {}", main.getTotalTimeSeconds());
  }
}
