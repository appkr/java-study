package ddd.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "shipping_addresses")
public class ShippingAddress {

    @Id
    @GeneratedValue
    private Long id;

    private String zipCode;

    private String receipient;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public ShippingAddress(String zipCode, String receipient) {
        this.zipCode = zipCode;
        this.receipient = receipient;
    }

    public ShippingAddress() {
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
            "id=" + id +
            ", zipCode='" + zipCode + '\'' +
            ", receipient='" + receipient + '\'' +
            '}';
    }
}
