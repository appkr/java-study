package springstudy.cloudstream.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;

@EnableBinding(Source.class)
public class GreetingSource {

    private static final Logger log = LoggerFactory.getLogger(GreetingSource.class);

    @InboundChannelAdapter(Source.OUTPUT)
    public String greet() {
        log.debug("invoking greet...");
        return "hello world " + System.currentTimeMillis();
    }

}
