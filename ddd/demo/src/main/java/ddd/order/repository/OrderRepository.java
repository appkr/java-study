package ddd.order.repository;

import ddd.order.entity.Order;

public interface OrderRepository {

    void save(Order order);
    void delete(Order order);
    Order findOne(Long orderId);
}
