package springstudy.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncJob {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async
    public void asyncMethodWithVoidReturnType() throws InterruptedException {
        log.info("Executing method asynchronously");
        for (int i = 0; i < 5; i++) {
            log.info("Do something heavy");
            Thread.sleep(100);
        }
    }

    @Async
    public Future<String> asyncMethodWithReturnType() {
        log.info("Job received and handling the job");
        try {
            Thread.sleep(500);
            return new AsyncResult<>("Hello world");
        } catch (InterruptedException e) {
            //
        }

        return null;
    }

    @Async
    public void asyncMethodWithException(String var) throws Exception {
        log.info("Job received and handling the job");
        if (true == true) {
            throw new Exception("Exception occurred while handling async job");
        }
        log.info("Job handling done");
    }
}
