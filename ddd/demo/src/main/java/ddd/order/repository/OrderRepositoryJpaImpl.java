package ddd.order.repository;

import ddd.order.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderRepositoryJpaImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Order order) {
        em.persist(order);
    }

    @Override
    @Transactional
    public void delete(Order order) {
        em.remove(order);
    }

    @Override
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }
}
