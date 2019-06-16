package ddd.order.service;

import ddd.order.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceTest {

    @Test
    public void shouldAcceptCreditCardPaymentWhenOrderHasSpecialLineItem() {
        Order order = new Order();
        order.addLineItem(new LineItem("P-0004", "상품 D", 1000l, 2));
        order.addOrderPayment(new CreditCardPayment(2000l, "1234-123"));
        order.setShippingAddress(new ShippingAddress("12345", "SomeBody"));

        OrderService sut = new OrderService();
        sut.placeOrder(order);

        assertTrue(order instanceof Order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptMobilePhonePaymentWhenOrderHasSpecialLineItem() {
        Order order = new Order();
        order.addLineItem(new LineItem("P-0004", "상품 D", 1000l, 2));
        order.addOrderPayment(new MobilePhonePayment(2000l, "010-0000-0000"));
        order.setShippingAddress(new ShippingAddress("12345", "SomeBody"));

        OrderService sut = new OrderService();
        sut.placeOrder(order);
    }
}
