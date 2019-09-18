package springstudy.fbf;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test
    public void testFormat() {
        Item item = Item.of("name", 10, "홍길동");
        assertEquals("홍길동    ", item.format());
    }
}
