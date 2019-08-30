package springstudy.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EventsApplication.class})
public class CustomSpringEventTest {

    @Autowired
    private CustomSpringEventPublisher publisher;

    @Test
    public void test() {
        publisher.doStuffAndPublishAnEvent("Event message");
    }
}
