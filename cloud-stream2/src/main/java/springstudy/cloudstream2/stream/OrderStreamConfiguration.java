package springstudy.cloudstream2.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import springstudy.cloudstream2.repository.OrderRepository;

@EnableBinding(OrderProcessor.class)
public class OrderStreamConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${originator}")
    private String originator;

    private OrderRepository orderRepository;

    @Autowired
    public OrderStreamConfiguration(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @StreamListener(OrderProcessor.INPUT)
    public void processOrder(Event event) {
        if (! event.getOriginator().equals(originator)) {
            logger.info("An order has been received: {}", event);
            orderRepository.save(event.getSubject());
        } else {
            logger.info("An order has been created on this server: {}", event);
        }
    }
}
