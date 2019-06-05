package springstudy.events2.autoinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static int counter;

    @Override
    public void run(String... args) throws Exception {
        counter++;
        logger.info("Increment counter: {}", counter);
    }
}
