package springstudy.fbf;

import java.nio.charset.Charset;

public class Item {

    private static Charset DEFAULT_CHARSET = Charset.forName("EUC-KR");

    private String name;
    private int length;
    private String value;

    private Item(String name, int length, String value) {
        this.name = name;
        this.length = length;
        this.value = value;
    }

    public static Item of(String name, int length, String value) {
        return new Item(name, length, value);
    }

    public static Item of(String name, int length) {
        return new Item(name, length, null);
    }

    public String format() {
        StringBuffer formatted = new StringBuffer(this.value);
        while (formatted.toString().getBytes(DEFAULT_CHARSET).length < this.length) {
            formatted.append(' ');
        }

        return formatted.toString();
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Item{" +
            "name='" + name + '\'' +
            ", length=" + length +
            ", value='" + value + '\'' +
            '}';
    }
}
