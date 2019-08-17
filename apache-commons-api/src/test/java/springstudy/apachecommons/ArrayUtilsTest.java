package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import static org.apache.commons.lang3.ArrayUtils.*;
import static org.junit.Assert.*;

public class ArrayUtilsTest {

    @Test
    public void test() {
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[][] colorArray = {
            {"RED", "#FF0000"},
            {"GREEN", "#00FF00"},
            {"BLUE", "#0000FF"}
        };

        p(ArrayUtils.hashCode(intArray)); // 1180826996

        p(toMap(colorArray)); // {RED=#FF0000, GREEN=#00FF00, BLUE=#0000FF}

        pa(toArray("One", "Two")); // {One,Two}

        Integer[] clonedIntArray = ArrayUtils.clone(intArray);
        p(ArrayUtils.hashCode(intArray) + " / " + ArrayUtils.hashCode(clonedIntArray)); // 1180826996 / 1180826996
        p(intArray == clonedIntArray); // false

        p(nullToEmpty(null, Integer[].class)); // [Ljava.lang.Integer;@21165b2a
        pa(nullToEmpty(null, Integer[].class)); // {}

        // Date[] someDates = (Date[]) ArrayUtils.subarray(allDates, 2, 5);
        pa(subarray(intArray, 0, 3)); // {1,2,3}

        p(isSameLength(intArray, clonedIntArray)); // true
        p(getLength(intArray)); // 5

        p(isSameType(intArray, colorArray)); // false

        // MUTABLE
        reverse(intArray);
        pa(intArray); // {5,4,3,2,1}

        // MUTABLE
        // Swap index 0 and index 4
        swap(intArray, 1, 2);
        pa(intArray); // {5,3,4,2,1}

        // MUTABLE
        shift(intArray, 1);
        pa(intArray); // {1,5,3,4,2}

        p(indexOf(intArray, 3)); // 2
        p(lastIndexOf(intArray, 3)); // 2

        p(contains(intArray, 3)); // true
        p(contains(intArray, 6)); // false

        int[] primitives = toPrimitive(new Integer[]{1, 2, 3});
        p(primitives); // [I@3bc2ad4d
        pa(toObject(primitives)); // {1,2,3}

        p(isEmpty(intArray)); // false
        p(isNotEmpty(intArray)); // true

        // Immutable
        pa(addAll(intArray, 1, 2, 3)); // {1,5,3,4,2,1,2,3}
        pa(addAll(intArray, null, 5, 6)); // {1,5,3,4,2,<null>,5,6}
        pa(intArray); // {1,5,3,4,2}

        pa(add(intArray, 6)); // {1,5,3,4,2,6}
        pa(add(intArray, null)); // {1,5,3,4,2,<null>}
        pa(intArray); // {1,5,3,4,2}

        pa(remove(intArray, 0)); // {5,3,4,2}
        pa(intArray); // {1,5,3,4,2}

        pa(removeElement(intArray, 3)); // {1,5,4,2}
        pa(intArray); // {1,5,3,4,2}

        pa(removeAll(intArray, 0, 1, 2)); // {4,2}
        pa(intArray); // {1,5,3,4,2}

        pa(removeElements(intArray, 1, 5, 3)); // {4,2}
        pa(intArray); // {1,5,3,4,2}

        p(isSorted(intArray)); // false

        pa(toStringArray(intArray)); // {1,5,3,4,2}

        pa(insert(0, intArray, 5, 6)); // {5,6,1,5,3,4,2}
        pa(intArray); // {1,5,3,4,2}

        // IMMUTABLE
        shuffle(intArray);
        pa(intArray); // {3,4,1,2,5}

    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }
}
