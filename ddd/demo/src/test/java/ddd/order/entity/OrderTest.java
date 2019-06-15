package ddd.order.entity;

import ddd.order.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderTest {

    @Test
    public void canPlaceAnOrder() {
        Order order = new Order();

        order.addLineItem(new LineItem("P-0001", "상품 A", 1000l, 2));
        order.addLineItem(new LineItem("P-0002", "상품 B", 2000l, 1));

        order.addOrderPayment(new CreditCardPayment(2000l, "1234-123"));
        order.addOrderPayment(new MobilePhonePayment(2000l, "010-0000-0000"));

        order.setShippingAddress(new ShippingAddress("12345", "SomeBody"));

        System.out.println("order = " + order);
        assertTrue(order instanceof Order);
    }
}
