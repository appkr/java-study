package springstudy.fbf;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Packet {

    private List<Item> items = new ArrayList<>();

    public String format() {
        StringBuffer formatted = new StringBuffer();
        for (Item item : items) {
            formatted.append(item.format());
        }

        return formatted.toString();
    }

    public void parse(byte[] src) {
        int srcPos = 0;
        for (Item item : items) {
            byte[] dest = new byte[item.getLength()];
            System.arraycopy(src, srcPos, dest, 0, item.getLength());
            srcPos += item.getLength();
            String str = new String(dest, Charset.forName("EUC-KR")).trim();
            item.setValue(str);
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Item getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public String toString() {
        return "Packet{" +
            "items=" + items +
            '}';
    }
}
