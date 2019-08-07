package springstudy.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static springstudy.querydsl.QCustomer.customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuerydslApplication.class})
public class QuerydslWithBooleanBuilderTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void testBooleanBuilder() {
        createCustomer("John", "Doe");
        createCustomer("Jane", "Doe");

        BooleanBuilder predicate = new BooleanBuilder();
        for (String token : Arrays.asList("John", "Doe", "Jane")) {
            predicate.or(customer.firstName.equalsIgnoreCase(token)
                .or(customer.lastName.equalsIgnoreCase(token)));
            // where lower(first_name) = ?
            //   or lower(last_name) = ?
            //   or lower(first_name) = ?
            //   or lower(last_name) = ?
            //   or lower(first_name) = ?
            //   or lower(last_name) = ?
        }

        List<Customer> res = queryFactory.selectFrom(customer).where(predicate).fetch();
        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");
    }

    private Customer createCustomer(String firstName, String lastName) {
        Customer customer = Customer.of(firstName, lastName);
        customerRepository.save(customer);

        return customer;
    }

    private Order createOrder(Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        orderRepository.save(order);

        return order;
    }
}
