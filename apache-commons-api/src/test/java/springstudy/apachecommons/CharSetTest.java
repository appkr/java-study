package springstudy.apachecommons;

import org.apache.commons.lang3.CharSet;
import org.junit.Test;

public class CharSetTest {

    @Test
    public void test() {
        CharSet charSet = CharSet.getInstance("foo", "bar", "baz", "qux");

        p(charSet.contains('y')); // false

        p(CharSet.ASCII_ALPHA); // [a-z, A-Z]
        p(CharSet.ASCII_ALPHA_LOWER); // [a-z]
        p(CharSet.ASCII_ALPHA_UPPER); // [A-Z]
        p(CharSet.ASCII_NUMERIC); // [0-9]
        p(CharSet.EMPTY); // []
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
