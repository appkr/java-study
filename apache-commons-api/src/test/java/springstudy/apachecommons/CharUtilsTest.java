package springstudy.apachecommons;

import org.junit.Test;

import static org.apache.commons.lang3.CharUtils.isAscii;
import static org.apache.commons.lang3.CharUtils.isAsciiPrintable;

public class CharUtilsTest {

    @Test
    public void test() {
        p(isAscii('a')); // true
        p(isAscii('가')); // false

        // 7 bit printable
        p(isAsciiPrintable('1')); // true
        p(isAsciiPrintable('가')); // false
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
