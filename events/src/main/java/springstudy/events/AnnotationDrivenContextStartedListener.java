package springstudy.events;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationDrivenContextStartedListener {

    @EventListener
    public void handleContextStarted(ContextStartedEvent event) {
        System.out.println("Handling context started event. ");
    }
}
