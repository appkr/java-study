package springstudy.apachecommons;

import org.junit.Test;

import static org.apache.commons.lang3.RandomStringUtils.*;

public class RandomStringUtilsTest {

    @Test
    public void test() {
        p(random(10)); // 𤋷龭폿쨹﹇첽䳳嫻ᘋ

        p(randomAscii(10)); // :LVO%_|*)d
        p(randomAscii(5, 10)); // YX'%Gm{>

        p(randomAlphabetic(10)); // sbSkFAxrZX
        p(randomAlphabetic(5, 10)); // qIMurAXix

        p(randomAlphanumeric(10)); // 6yaGwFxbe4
        p(randomAlphanumeric(5, 10)); // LdK14v3Du

        // POSIX [:graph:] regular expression character class
        p(randomGraph(10)); // M.ht&}Ph8=
        p(randomGraph(5, 10)); // UMxB^

        p(randomNumeric(10)); // 0805579533
        p(randomNumeric(5, 10)); // 988957570

        // POSIX [:print:] regular expression character class
        p(randomPrint(10)); // 6G7GJQzGY_
        p(randomPrint(5, 10)); // R40.=
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
