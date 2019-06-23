package springstudy.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncJobTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AsyncJob job;

    @Test
    public void asyncMethodWithVoidReturnType() throws InterruptedException {
        log.info("Delegating job to worker method");
        job.asyncMethodWithVoidReturnType();
        log.info("Delegating done");
        Thread.sleep(500);
    }

    @Test
    public void asyncMethodWithReturnType() throws ExecutionException, InterruptedException {
        log.info("Delegating job to worker method");
        Future<String> future = job.asyncMethodWithReturnType();

        while(true) {
            if (future.isDone()) {
                log.info("Result from asynchronous process: {}", future.get());
                break;
            }
            Thread.sleep(100);
            log.info("Continue doing something else");
        }
    }

    @Test
    public void asyncMethodWithException() throws Exception {
        log.info("Delegating job to worker method");
        job.asyncMethodWithException("foo");
        log.info("Delegating done");
        Thread.sleep(5000);
    }
}