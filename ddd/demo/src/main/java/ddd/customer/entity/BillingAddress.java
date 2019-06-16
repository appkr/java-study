package ddd.customer.entity;

import javax.persistence.*;

@Entity
@Table(name = "billing_addresses")
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "BillingAddress{" +
            "id=" + id +
            ", zipCode='" + zipCode + '\'' +
            '}';
    }
}
