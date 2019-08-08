package springstudy.querydsl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 60)
    private String firstName;

    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(name = "tags", columnDefinition = "text")
    @Convert(converter = TagConverter.class)
    private SortedSet<String> tags = new TreeSet<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer() { }

    private Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Customer of(String firstName, String lastName) {
        return new Customer(firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SortedSet<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tagList) {
        TreeSet<String> newTags = new TreeSet<>();
        if (tagList != null) {
            tagList.sort(String::compareToIgnoreCase);
            newTags.addAll(tagList);
        }

        this.tags = newTags;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        orders.forEach(e -> e.setCustomer(this));
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", tags=" + tags +
            '}';
    }
}
