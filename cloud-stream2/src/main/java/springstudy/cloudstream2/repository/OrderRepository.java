package springstudy.cloudstream2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springstudy.cloudstream2.data.Order;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
}
