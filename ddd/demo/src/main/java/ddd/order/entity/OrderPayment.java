package ddd.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_payments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "method", discriminatorType = DiscriminatorType.STRING)
public class OrderPayment {

    @Id
    @GeneratedValue
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, insertable = false, updatable = false)
    protected PaymentMethod method;

    protected Long amount;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    protected Order order;

    public enum PaymentMethod {
        CREDIT_CARD,
        MOBILE_PHONE;

        static class Values {
            static final String CREDIT_CARD = "CREDIT_CARD";
            static final String MOBILE_PHONE = "MOBILE_PHONE";
        }
    }

    public OrderPayment(PaymentMethod method, Long amount) {
        this.method = method;
        this.amount = amount;
    }

    public OrderPayment() {
    }

    public boolean isCreditCard() {
        return this.method.equals(PaymentMethod.CREDIT_CARD);
    }

    public boolean isMobilePhone() {
        return this.method.equals(PaymentMethod.MOBILE_PHONE);
    }
}
