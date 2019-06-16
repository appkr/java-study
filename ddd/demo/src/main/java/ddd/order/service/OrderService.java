package ddd.order.service;

import ddd.order.entity.Order;
import ddd.order.entity.ShippingAddress;
import ddd.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(Order order) {
        order.placeOrder();
        saveOrder(order);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void changeShippingAddress(final Long orderId, final ShippingAddress shippingAddress) {
        Order order = orderRepository.findOne(orderId);
        order.changeShippingAddress(shippingAddress);
    }
}
