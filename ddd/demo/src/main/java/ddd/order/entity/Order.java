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

    @OneToMany(mappedBy = "order")
    private List<LineItem> lineItems = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderPayment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "order")
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

    public void addOrderPayment(OrderPayment payment) {
        this.payments.add(payment);
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
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
