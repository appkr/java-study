package ddd.order.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = OrderPayment.PaymentMethod.Values.CREDIT_CARD)
public class CreditCardPayment extends OrderPayment{

    private String cardNumber;

    public CreditCardPayment(Long amount, String cardNumber) {
        super(PaymentMethod.CREDIT_CARD, amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CreditCardPayment{" +
            "cardNumber='" + cardNumber + '\'' +
            ", id=" + id +
            ", method=" + method +
            ", amount=" + amount +
            '}';
    }
}
