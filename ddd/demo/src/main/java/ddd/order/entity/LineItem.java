package ddd.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "line_items")
public class LineItem {

    @Id
    @GeneratedValue
    private Long id;

    private String productId;

    private String name;

    private Long price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public LineItem(String productId, String name, Long price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public LineItem() {
    }

    public String getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "LineItem{" +
            "id=" + id +
            ", productId='" + productId + '\'' +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
    }
}
