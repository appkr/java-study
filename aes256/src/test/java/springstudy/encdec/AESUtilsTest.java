package springstudy.encdec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AESUtilsTest {

    @Test
    public void testEncodeAndDecode() {
        String src = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";

        String encoded = AESUtils.encode(src);
        System.out.println(encoded);

        String decoded = AESUtils.decode(encoded);
        System.out.println(decoded);

        assertEquals(src, decoded);
    }
}
