package springstudy.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Exception message: {}", ex.getMessage());
        for (Object param: params) {
            log.info("Parameters: {}", param);
        }
    }
}
