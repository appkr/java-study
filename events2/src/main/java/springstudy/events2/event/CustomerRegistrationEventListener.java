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
    @EventListener(condition = "#event.customerType == T(springstudy.events2.event.CustomerRegistrationEvent.CustomerType).B2B")
    public void handleB2B(CustomerRegistrationEvent event) {
        logger.info("REGISTRATION EVENT GOT RECEIVED FOR B2B CUSTOMER : {}", event);
    }

    @Async
    @EventListener(condition = "#event.customerType.name() == 'B2C'")
    public void handleB2C(CustomerRegistrationEvent event) {
        logger.info("REGISTRATION EVENT GOT RECEIVED FOR B2c CUSTOMER : {}", event);
    }
}
