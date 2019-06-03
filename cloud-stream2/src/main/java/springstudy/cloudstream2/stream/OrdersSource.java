package springstudy.cloudstream2.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class OrdersSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private OrderProcessor orderOut;

    @Autowired
    public OrdersSource(OrderProcessor orderOut) {
        this.orderOut = orderOut;
    }

    public void sendOrder(Event event) {
        orderOut.ordersOut().send(new GenericMessage<>(event));
        logger.info("Event sent: {}", event);
    }
}
