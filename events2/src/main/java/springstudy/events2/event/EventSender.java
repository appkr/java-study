package springstudy.events2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventSender implements ApplicationRunner {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CustomerRegistrationEvent eventB2B = new CustomerRegistrationEvent("Foo", CustomerRegistrationEvent.CustomerType.B2B);
        CustomerRegistrationEvent eventB2C = new CustomerRegistrationEvent("Foo", CustomerRegistrationEvent.CustomerType.B2C);
        publisher.publishEvent(eventB2B);
        publisher.publishEvent(eventB2C);
    }
}
