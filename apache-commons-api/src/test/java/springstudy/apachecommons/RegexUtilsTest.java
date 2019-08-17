package springstudy.apachecommons;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.RegExUtils.*;

public class RegexUtilsTest {

    @Test
    public void test() {
        String text = "FooBar";

        p(removeAll(text, Pattern.compile("B"))); // Fooar
        p(removeAll(text, "B")); // Fooar

        p(removeFirst(text, Pattern.compile("o"))); // FoBar
        p(removeFirst(text, "o")); // FoBar

        p(removePattern(text, "[o|a|r]")); // FB

        p(replaceAll(text, Pattern.compile("[o]"), "O")); // FOOBar
        p(replaceAll(text, "[o]", "O")); // FOOBar
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
