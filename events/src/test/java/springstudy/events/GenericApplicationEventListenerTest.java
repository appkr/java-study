package springstudy.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EventsApplication.class})
public class GenericApplicationEventListenerTest {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void test() {
        GenericApplicationEvent<String> event = new GenericApplicationEvent<>("Event message", true);
        publisher.publishEvent(event);
    }

}
