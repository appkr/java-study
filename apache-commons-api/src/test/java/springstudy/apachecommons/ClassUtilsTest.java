package springstudy.apachecommons;

import org.junit.Test;

import java.util.Arrays;

import static org.apache.commons.lang3.ClassUtils.*;

public class ClassUtilsTest {

    @Test
    public void test() {
        Integer i = 1;

        p(getShortClassName(Integer.class)); // Integer
        p(getShortClassName(i, "Unknown")); // Integer

        p(getSimpleName(Integer.class)); // Integer
        p(getSimpleName(i)); // Integer

        p(getName(Integer.class)); // java.lang.Integer
        p(getName(i)); // java.lang.Integer

        p(getPackageName(Integer.class)); // java.lang
        p(getPackageName(i, "Unknown")); // java.lang

        p(getAbbreviatedName(Integer.class, 1)); // j.l.Integer

        p(getAllSuperclasses(Integer.class)); // [class java.lang.Number, class java.lang.Object]

        p(getAllInterfaces(Integer.class)); // [interface java.lang.Comparable, interface java.io.Serializable]

        p(convertClassNamesToClasses(Arrays.asList("java.lang.Integer"))); // [class java.lang.Integer]

        p(Integer.class); // class java.lang.Integer

        p(isAssignable(Integer.class, String.class)); // false
        p(isAssignable(String.class, Integer.class)); // false

        p(isPrimitiveOrWrapper(void.class)); // true
        p(isPrimitiveOrWrapper(Integer.class)); // true

        p(primitiveToWrapper(Integer.class)); // class java.lang.Integer
        p(primitiveToWrapper(i.getClass())); // class java.lang.Integer

        p(wrapperToPrimitive(i.getClass())); // int

        p(getShortCanonicalName(Integer.class)); // Integer
        p(getCanonicalName(Integer.class)); // java.lang.Integer

        pl(hierarchy(Integer.class));
        // class java.lang.Number
        // class java.lang.Object
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pl(Iterable iter) {
        iter.forEach(e -> System.out.println(e));
    }
}
