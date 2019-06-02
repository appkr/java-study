package springstudy.cloudstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class CustomProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyProcessor processor;

    public static void main(String[] args) {
        SpringApplication.run(CustomProcessorApplication.class, args);
    }

    @StreamListener(MyProcessor.INPUT)
    public void routeValues(Integer val) {
        logger.info("A message received from myInput: {}", val);
        if (val < 10) {
            processor.anOutput().send(new GenericMessage<>(val));
        } else {
            processor.anotherOutput().send(new GenericMessage<>(val));
        }
    }
}
