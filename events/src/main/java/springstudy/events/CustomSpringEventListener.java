package springstudy.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("---");
        System.out.println("Received spring custom event: " + event.getMessage());
        System.out.println("---");
    }
}
