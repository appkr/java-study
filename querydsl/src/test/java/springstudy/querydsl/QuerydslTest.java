package springstudy.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static springstudy.querydsl.QCustomer.customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuerydslApplication.class})
public class QuerydslTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JPAQueryFactory queryFactory;
    
    @Test
    public void testBasicQuery() {
        createCustomer("John", "Doe");

        List<Customer> johnDoe = queryFactory.selectFrom(customer)
            .where(
                customer.firstName.eq("John"),
                customer.lastName.containsIgnoreCase("Doe"),
                customer.firstName.likeIgnoreCase("Foo"))
            .fetch();
        // where first_name = ?
        //  and (lower(last_name) like ? escape '!')
        //  and (lower(first_name) like ?)
        // John, doe, %foo%

        System.out.println("---");
        System.out.println(johnDoe);
        System.out.println("---");
        assertTrue(true);
    }

    @Test
    @Rollback(value = false)
    public void testSort() {
        createCustomer("B", "Doe");
        createCustomer("A", "Doe");

        List<Customer> res = queryFactory.selectFrom(customer)
            .orderBy(customer.firstName.asc(),
                customer.lastName.asc())
            .fetch();
        // order by first_name asc, last_name asc

        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");
        assertTrue(true);
    }

    @Test
    @Ignore
    public void testSubQuery1() {
        Customer john = createCustomer("John", "Doe");
        createOrder(john);
        createOrder(john);
        createOrder(john);

        Customer foo = createCustomer("Foo", "Bar");
        createOrder(foo);
        createOrder(foo);

        List<Customer> res = queryFactory.selectFrom(customer)
            .where(customer.orders.size().eq(
                JPAExpressions.select(customer.orders.size().max()).from(customer)))
            .fetch();

        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");
        assertTrue(true);
    }

    @Test
    public void testSubQuery2() {
        Customer johnDoe = createCustomer("John", "Doe");
        Customer janeDoe = createCustomer("Jane", "Doe");
        createOrder(johnDoe);
        createOrder(johnDoe);
        createOrder(janeDoe);

        List<Customer> res = queryFactory.selectFrom(customer)
            .where(customer.orders.size().gt(1))
            .fetch();
        //select *
        //from customers
        //where (
        //    select count(customer_id)
        //    from orders
        //    where id = customer_id
        //) > ?

        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");

        assertTrue(true);
    }

    @Test
    public void testTuple() {
        createCustomer("John", "Doe");

        Tuple res = queryFactory
            .select(customer.firstName, customer.lastName)
            .from(customer)
            .fetchOne();

        System.out.println("---");
        System.out.println(res.get(customer.firstName));
        System.out.println(res.get(1, String.class));
        System.out.println("---");
        assertTrue(true);
    }

    @Test
    public void testJoin() {
        Order order = createOrder(createCustomer("John", "Doe"));

        JPAQuery<Customer> query = queryFactory.selectFrom(customer)
            .leftJoin(customer.orders)
            .on(customer.firstName.equalsIgnoreCase("john"));

        Customer john = query.fetchFirst();

        System.out.println("---");
        System.out.println(query.createQuery().getSingleResult());
        System.out.println(john);
        System.out.println("---");
        assertTrue(true);
    }

    @Test
    public void testGroupBy() {
        createCustomer("John", "Doe");
        createCustomer("Jane", "Doe");

        List<String> res = queryFactory.from(customer)
            .groupBy(customer.lastName)
            .select(customer.lastName)
            .fetch();

        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");

        assertTrue(true);
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
