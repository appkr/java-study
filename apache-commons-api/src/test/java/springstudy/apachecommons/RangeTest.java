package springstudy.apachecommons;

import org.apache.commons.lang3.Range;
import org.junit.Test;

import static org.apache.commons.lang3.Range.*;

public class RangeTest {

    @Test
    public void test() {
        Range<String> range = between("a", "z");
        p(range); // [a..z]

        p(range.contains("d")); // true
        p(range.contains("z")); // true
        p(range.contains("A")); // false

        p(range.containsRange(between("c", "u"))); // true
        p(range.containsRange(between("z", "A"))); // false

        p(range.getMaximum()); // z
        p(range.getMinimum()); // a

        p(range.intersectionWith(between("A", "c"))); // [a..c]

        p(range.isAfter("Z")); // true
        p(range.isAfter("a")); // false

        p(range.isBefore("가")); // true

        // Overlap as least once
        p(range.isOverlappedBy(between("Z", "o"))); // true
        p(range.isOverlappedBy(between("A", "Z"))); // false
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
