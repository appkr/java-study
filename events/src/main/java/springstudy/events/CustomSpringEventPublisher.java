package springstudy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventPublisher {

    private ApplicationEventPublisher publisher;

    public CustomSpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void doStuffAndPublishAnEvent(final String message) {
        System.out.println("Publishing custom event. ");
        CustomSpringEvent event = new CustomSpringEvent(this, message);
        publisher.publishEvent(event);
    }
}
