package ddd.order.service;

import ddd.order.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void placeOrder(Order order) {
        order.placeOrder();
    }
}
