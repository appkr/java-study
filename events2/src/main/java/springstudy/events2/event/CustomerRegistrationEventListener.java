package springstudy.events2.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegistrationEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @EventListener
    public void handle(CustomerRegistrationEvent event) {
        logger.info("Registration event got received for customer : {}", event.getName());
    }
}
