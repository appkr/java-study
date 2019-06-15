package ddd.order.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = OrderPayment.PaymentMethod.Values.MOBILE_PHONE)
public class MobilePhonePayment extends OrderPayment {

    private String phoneNumber;

    public MobilePhonePayment(Long amount, String phoneNumber) {
        super(PaymentMethod.MOBILE_PHONE, amount);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "MobilePhonePayment{" +
            "phoneNumber='" + phoneNumber + '\'' +
            ", id=" + id +
            ", method=" + method +
            ", amount=" + amount +
            '}';
    }
}
