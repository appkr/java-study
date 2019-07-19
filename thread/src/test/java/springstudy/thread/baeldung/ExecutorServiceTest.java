package springstudy.thread.baeldung;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class ExecutorServiceTest {

    @Test
    public void 설정한_개수의_스레드로_작업을_처리한다() throws ExecutionException, InterruptedException {
        log.info("Thread name: {}", Thread.currentThread().getName());

        ExecutorService es = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            Future<String> future = es.submit(() -> {
                // NOTE. 리턴 값을 Future로 받는다.
                return "Hello Thread from " + Thread.currentThread().getName();
            });

            log.info(future.get());
        }
    }
}
