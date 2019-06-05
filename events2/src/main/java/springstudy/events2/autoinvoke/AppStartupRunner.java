package springstudy.events2.autoinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static int counter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        counter++;
        logger.info("Application started with option names: {}", args.getOptionNames());
        logger.info("Increment counter: {}", counter);
    }
}
