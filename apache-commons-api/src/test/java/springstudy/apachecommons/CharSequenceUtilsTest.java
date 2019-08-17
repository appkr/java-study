package springstudy.apachecommons;

import org.apache.commons.lang3.CharSequenceUtils;
import org.junit.Test;

import static org.apache.commons.lang3.CharSequenceUtils.*;

public class CharSequenceUtilsTest {

    @Test
    public void test() {
        String sut = "foobar";

        p(subSequence(sut, 1)); // oobar
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
