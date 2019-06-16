package ddd.order.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<LineItem> lineItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderPayment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private ShippingAddress shippingAddress;

    private LocalDateTime createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public Order() {
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void addOrderPayment(OrderPayment payment) {
        this.payments.add(payment);
    }

    public List<OrderPayment> getPayments() {
        return payments;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        // Validation logic here
        this.shippingAddress = shippingAddress;
    }

    public void changeShippingAddress(ShippingAddress shippingAddress) {
        // Validation logic here
        setShippingAddress(shippingAddress);
    }

    public void placeOrder() {
        this.validate();
        // Other validation policy
    }

    private void validate() {
        this.validatePaymentMethodPolicyOfLineItems();
    }

    private void validatePaymentMethodPolicyOfLineItems() {
        boolean contains = this.getLineItems()
            .stream()
            .anyMatch(
                lineItem -> lineItem
                    .getProductId()
                    .equalsIgnoreCase("P-0004")
            );

        if (contains) {
            if (this.getPayments().size() != 1 ||
                this.getPayments()
                    .stream()
                    .anyMatch(
                        orderPayment -> !orderPayment.isCreditCard()
                    )
            ) {
                throw new IllegalArgumentException("P-0004 상품은 신용카드로만 결제가 가능합니다");
            }
        }
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", createdAt=" + createdAt +
            ", lineItems=" + lineItems +
            ", payments=" + payments +
            ", shippingAddress=" + shippingAddress +
            '}';
    }
}
