package ddd.customer.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BillingAddress> billingAddresses = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void addBillingAddresses(BillingAddress billingAddress) {
        this.billingAddresses.add(billingAddress);
    }

    public String getName() {
        return name;
    }

    public List<BillingAddress> getBillingAddresses() {
        return billingAddresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "name='" + name + '\'' +
            ", billingAddresses=" + billingAddresses +
            '}';
    }
}
