package springstudy.events2.autoinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    static int counter;

    @EventListener
    public void handle(ContextRefreshedEvent event) {
        counter++;
        logger.info("Increment counter: {}", counter);
    }
}
