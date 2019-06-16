package ddd.customer.entity;

import ddd.order.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerTest {

    @Test
    public void canCreateCustomer() {
        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setZipCode("12345");

        Customer customer = new Customer();
        customer.setName("SomeBody");
        customer.addBillingAddresses(billingAddress);
        billingAddress.setCustomer(customer);

        System.out.println("customer = " + customer);
        assertTrue(billingAddress.getCustomer().equals(customer));
        assertTrue(billingAddress.equals(customer.getBillingAddresses().get(0)));
    }
}
