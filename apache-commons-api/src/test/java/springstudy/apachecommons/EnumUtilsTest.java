package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.apache.commons.lang3.EnumUtils.*;

public class EnumUtilsTest {

    @Test
    public void test() {
        Class<Gender> enumClass = Gender.class;

        p(getEnumMap(enumClass)); // {MALE=MALE, FEMALE=FEMALE}

        p(getEnumList(enumClass)); // [MALE, FEMALE]

        p(isValidEnumIgnoreCase(enumClass, "male")); // true
        p(isValidEnumIgnoreCase(enumClass, "UNKNOWN")); // false

        p(getEnumIgnoreCase(enumClass, "male")); // MALE
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
