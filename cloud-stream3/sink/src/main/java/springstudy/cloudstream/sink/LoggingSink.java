package springstudy.cloudstream.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class LoggingSink {

    private static final Logger log = LoggerFactory.getLogger(LoggingSink.class);

    @StreamListener(Sink.INPUT)
    public void log(String message) {
        log.info(message);
    }

}
