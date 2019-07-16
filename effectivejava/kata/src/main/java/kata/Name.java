package kata;

public class Name {

    public static Name EMPTY = new Name(null);

    private String value;

    private Name(String value) {
        this.value = value;
    }

    public static Name of(String value) {
        return new Name(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Name{" +
            "value='" + value + '\'' +
            '}';
    }
}
