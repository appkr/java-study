package springstudy.cloudstream2.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import springstudy.cloudstream2.data.Order;
import springstudy.cloudstream2.repository.OrderRepository;
import springstudy.cloudstream2.stream.Event;
import springstudy.cloudstream2.stream.OrdersSource;

@RestController
public class OrderController {

    private OrderRepository orderRepository;

    private OrdersSource orderSource;

    @Value("${originator}")
    private String originator;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrdersSource orderSource) {
        this.orderRepository = orderRepository;
        this.orderSource = orderSource;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Order> getOrder() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<Order> add(@RequestBody Order input) {
        Order order = orderRepository.save(input);
        Event event = new Event(order, "ORDER", originator);
        orderSource.sendOrder(event);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
}
