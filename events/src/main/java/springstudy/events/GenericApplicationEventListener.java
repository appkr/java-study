package springstudy.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GenericApplicationEventListener implements ApplicationListener<GenericApplicationEvent<Object>> {

    @Override
    public void onApplicationEvent(GenericApplicationEvent<Object> event) {
        System.out.println("---");
        System.out.println("Received generic application event: " + event.getWhat());
        System.out.println("---");
    }
}
