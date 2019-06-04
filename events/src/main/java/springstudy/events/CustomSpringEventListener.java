package springstudy.events;

import org.springframework.context.ApplicationListener;

public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("Received spring custom event: " + event.getMessage());
    }
}
