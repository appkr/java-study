package springstudy.apachecommons;

import org.junit.Test;

import static org.apache.commons.lang3.RandomUtils.*;

public class RandomUtilsTest {

    @Test
    public void test() {
        p(nextBoolean()); // false

        p(nextBytes(10)); // [B@6b670029

        p(nextInt(1, 10)); // 9
        p(nextInt()); // 1463264502

        p(nextLong(1L, 10L)); // 1
        p(nextLong()); // 867719451104825344

        p(nextDouble(0.0, 1.0)); // 0.7240604425428807
        p(nextDouble()); // 4.117847719512807E307

        p(nextFloat(0.0F, 1.0F)); // 0.5018491
        p(nextFloat()); // 2.3751105E37
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
