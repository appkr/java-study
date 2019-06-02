package springstudy.cloudstream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudStreamApplicationTests {

    @Autowired
    private Processor pipe;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void testShouldUpdateLogMessage() {
        Message<LogMessage> message = MessageBuilder.withPayload(new LogMessage("This is my message")).build();
        pipe.input().send(message);

        Object payload = messageCollector.forChannel(pipe.output())
            .poll()
            .getPayload();

        System.out.println("payload = " + payload.toString());

        assertTrue(payload.toString().contains("[1]:"));
    }
}
