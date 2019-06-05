package springstudy.events2.event;

public class CustomerRegistrationEvent {

    private String name;

    private CustomerType customerType;

    public CustomerRegistrationEvent(String name, CustomerType customerType) {
        this.name = name;
        this.customerType = customerType;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationEvent{" +
            "name='" + name + '\'' +
            ", customerType=" + customerType +
            '}';
    }

    public enum CustomerType {
        B2B, B2C;
    }
}
