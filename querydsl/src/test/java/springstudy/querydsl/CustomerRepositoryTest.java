package springstudy.querydsl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuerydslApplication.class})
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void testCanRetrieveCustomerByName() {
        Customer sut = Customer.of("John", "Doe");
        repository.save(sut);

        Optional<Customer> optional = repository.findByName("John");
        Customer customer = optional.get();

        System.out.println("---");
        System.out.println(customer);
        System.out.println("---");

        assertNotNull(customer);
    }
}
