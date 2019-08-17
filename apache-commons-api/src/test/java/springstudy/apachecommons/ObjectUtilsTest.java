package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import static org.apache.commons.lang3.ObjectUtils.*;

public class ObjectUtilsTest {

    @Test
    public void test() {
        p(allNotNull(1, 2, 3)); // true
        p(allNotNull(1, null, 3)); // false

        p(min(3, 1, 2)); // 1
        p(max("가", "다", "나")); // 다

        p(compare(2, 1)); // 1
        p(compare(1, 1)); // 0
        p(compare(1, 2)); // -1

        p(median("a", "c", "c", "b")); // b
        p(mode("a", "c", "c", "b")); // c

        /**
         * NOTE. Must implement Cloneable interface and clone() method
         * super(Object) class's clone() method has protected visibility
         */
        Name name = new Name("Foo", "Bar");
        Name cloned = ObjectUtils.clone(name);
        p(name.hashCode()); // 1343159359
        p(cloned.hashCode()); // 275597150
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }
}
