package springstudy.events2.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegistrationEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    @EventListener
    public void handle(CustomerRegistrationEvent event) {
        logger.info("REGISTRATION EVENT GOT RECEIVED FOR CUSTOMER : {}", event.getName());
    }
}
