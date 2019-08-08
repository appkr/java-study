package springstudy.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
    private JPAQueryFactory queryFactory;

    @Test
    public void testBooleanBuilder() {
        createCustomer("John", "Doe");
        createCustomer("Jane", "Doe");

        BooleanBuilder predicate = new BooleanBuilder();
        for (String token : Arrays.asList("John", "Doe", "Jane")) {
            predicate.or(customer.firstName.equalsIgnoreCase(token)
                .or(customer.lastName.likeIgnoreCase(token)));
            // where lower(first_name) = ?
            //   or lower(last_name) like ?
            //   or lower(first_name) = ?
            //   or lower(last_name) like ?
            //   or lower(first_name) = ?
            //   or lower(last_name) like ?
            //john, john, doe, doe, jane, jane
        }

        List<Customer> res = queryFactory.selectFrom(customer).where(predicate).fetch();
        System.out.println("---");
        res.forEach(System.out::println);
        System.out.println("---");
    }

    @Test
    public void testContains() {
        Customer sut = Customer.of("Foo", "Bar");
        sut.setTags(Arrays.asList("나", "가", "다"));
        customerRepository.save(sut);               // insert into customers (first_name, last_name, tags, id)
                                                    //                values ('Foo', 'Bar', '["가","나","다"]', 1)

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(customer.tags.contains("가"));
        JPAQuery<Customer> query = queryFactory.selectFrom(customer).where(predicate);

        System.out.println("---");
        System.out.println(predicate.getValue());   // 가 in customer.tags
        System.out.println(query);                  // select customer from Customer customer where ?1 member of customer.tags
        System.out.println("---");

        List<Customer> res = query.fetch();         // java.lang.NullPointerException
    }

    @Test
    public void testComplexQuery() {
        Customer sut = Customer.of("Foo", "Bar");
        sut.setTags(Arrays.asList("나", "가", "다"));
        customerRepository.save(sut);

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(customer.firstName.eq("Foo"));

//        BooleanExpression[] exp = new BooleanExpression[2];
//        Arrays.fill(exp, customer.tags.contains("가"));
//        Arrays.fill(exp, customer.tags.contains("하"));
//        predicate.andAnyOf(exp);

        predicate.andAnyOf(customer.tags.contains("가"), customer.tags.contains("하"));

        JPAQuery<Customer> query = queryFactory.selectFrom(customer).where(predicate);

        System.out.println("---");
        System.out.println(predicate.getType());    // class java.lang.Boolean
        System.out.println(predicate.getValue());   // customer.firstName = Foo && (가 in customer.tags || 하 in customer.tags)
        System.out.println(query);                  // select customer from Customer customer where customer.firstName = ?1 and (?2 member of customer.tags or ?3 member of customer.tags)
        System.out.println("---");

        List<Customer> res = query.fetch();         // java.lang.NullPointerException
    }

    private Customer createCustomer(String firstName, String lastName) {
        Customer customer = Customer.of(firstName, lastName);
        customerRepository.save(customer);

        return customer;
    }
}
