package springstudy.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;
import static springstudy.querydsl.QCustomer.customer;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CustomerRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> res = queryFactory.selectFrom(customer)
            .where(customer.firstName.eq(name).or(customer.lastName.eq(name)))
            .limit(1L)
            .fetch();

        return Optional.ofNullable(res.get(0));
    }
}
