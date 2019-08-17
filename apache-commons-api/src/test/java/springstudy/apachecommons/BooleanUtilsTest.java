package springstudy.apachecommons;

import org.junit.Test;

import static org.apache.commons.lang3.BooleanUtils.*;

public class BooleanUtilsTest {

    @Test
    public void test() {
        p(negate(true)); // false

        p(isTrue(true)); // true
        p(isNotTrue(false)); // true

        p(isFalse(false)); // true
        p(isNotFalse(true)); // true

        p(toBoolean(true)); // true
        p(toBoolean("")); // false
        p(toBoolean(0)); // false

        p(toBooleanObject(1)); // true
        p(toBooleanObject(10)); // true
        p(toBooleanObject(0)); // false
        p(toBooleanObject(-1)); // true
        p(toBooleanObject(-10)); // true

        p(toBooleanObject("true")); // true
        p(toBooleanObject("false")); // false
        p(toBooleanObject("Y")); // true
        p(toBooleanObject("N")); // false
        p(toBooleanObject("1")); // null
        p(toBooleanObject("0")); // null

        p(toInteger(true)); // 1
        p(toInteger(false)); // 0

        p(toStringYesNo(true)); // yes
        p(toStringYesNo(false)); // not
        p(toStringTrueFalse(true)); // true
        p(toStringTrueFalse(false)); // false
        p(toStringOnOff(true)); // on
        p(toStringOnOff(false)); // off

        p(and(new Boolean[] {true, true})); // true
        p(and(new Boolean[] {true, false})); // false

        p(or(new Boolean[] {true, true})); // true
        p(or(new Boolean[] {true, false})); // true

        p(xor(new Boolean[] {true, true})); // false
        p(xor(new Boolean[] {true, false})); // true
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
