package module8;

public class Person {

    private String lastName;
    private String firstName;

    // NOTE.
    // without Empty Constructor...
    // Caused by: java.lang.reflect.UndeclaredThrowableException: null
    //	at org.springframework.util.ReflectionUtils.handleReflectionException(ReflectionUtils.java:322) ~[spring-core-5.1.5.RELEASE.jar:5.1.5.RELEASE]
    // Caused by: java.lang.InstantiationException: module8.Person
    //	at java.lang.Class.newInstance(Class.java:427) ~[na:1.8.0_162]
    public Person() {
    }

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Person{" +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
    }

}
