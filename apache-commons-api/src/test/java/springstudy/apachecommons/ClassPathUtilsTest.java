package springstudy.apachecommons;

import org.junit.Test;

import static org.apache.commons.lang3.ClassPathUtils.*;

public class ClassPathUtilsTest {

    @Test
    public void test() {
        p(toFullyQualifiedName(Integer.class, "foo")); // java.lang.foo
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
