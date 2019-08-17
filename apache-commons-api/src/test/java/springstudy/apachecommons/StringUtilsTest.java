package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import sun.nio.cs.US_ASCII;

import static org.apache.commons.lang3.StringUtils.*;

public class StringUtilsTest {

    @Test
    public void test() {
        String text = "FooBar";

        p(abbreviate(text, 4)); // F...

        p(appendIfMissingIgnoreCase(text, "_suffix")); // FooBar_suffix

        p(capitalize("fooBar")); // FooBar

        p(center(text, 10)); //   FooBar

        p(chomp(text+"\n")); // FooBar

        p(chop(text)); // FooBa

        p(compareIgnoreCase("a", "b")); // -1
        p(compareIgnoreCase("a", "a")); // 0
        p(compareIgnoreCase("b", "a")); // 1

        p(containsAny(text, "ar")); // true
        p(containsAny(text, "z")); // false

        p(containsIgnoreCase(text, "f")); // true

        p(containsWhitespace(text)); // false

        p(countMatches(text, "o")); // 2

        p(defaultIfEmpty(text, "default")); // FooBar
        p(defaultIfEmpty("", "default")); // default

        p(endsWithIgnoreCase(text, "bar")); // true
        p(startsWithIgnoreCase(text, "foo")); // true

        p(prependIfMissing(text, "prefix_")); // prefix_FooBar

        p(indexOf(text, "F")); // 0
        p(indexOf(text, "z")); // -1
        p(indexOfAny(text, 'o', 'a')); // 1

        p(isAllBlank(null)); // true
        p(isAllBlank("")); // true
        p(isAllBlank(null, "", text)); // false

        p(isAllEmpty(null)); // true
        p(isAllEmpty("")); // true
        p(isAllEmpty(new String[] {})); // true
        p(isAllEmpty(null, "", text)); // false

        p(isAlpha(null)); // false
        p(isAlpha("")); // false
        p(isAlpha(text)); // true
        p(isAlpha("abc")); // true

        p(isAlphanumeric(null)); // false
        p(isAlphanumeric(text + " " + "123")); // false
        p(isAlphanumeric(text + "123")); // true

        p(isBlank(null)); // true
        p(isBlank("")); // true
        p(isBlank(" ")); // true
        p(isNotBlank(null)); // false
        p(isNotBlank("")); // false
        p(isNotBlank(" ")); // false

        p(isEmpty(null)); // true
        p(isEmpty("")); // true
        p(isEmpty(" ")); // false
        p(isNotEmpty(null)); // false
        p(isNotEmpty("")); // false
        p(isNotEmpty(" ")); // true

        p(isNumeric(null)); // false
        // UTF character
        p(isNumeric("\u0967\u0968\u0969")); // true
        p(isNumeric("12 3")); // false
        p(isNumeric("123")); // true
        p(isNumeric("12.3")); // false

        byte[] bytes = text.getBytes();
        p(join(bytes)); // [B@2c3ab4ca
        p(join(new String[] {"1", "2"}, ",")); // 1,2
        p(join("Foo", "Bar", "Baz", "Qux")); // FooBarBazQux
        p(joinWith(",", "Foo", "Bar", "Baz", "Qux")); // Foo,Bar,Baz,Qux

        p(lastIndexOf(text, "o")); // 2

        p(left(text, 3)); // Foo

        p(leftPad(text, 10)); //     FooBar
        p(leftPad("123", 10, "0")); // 0000000123

        p(length(null)); // 0
        p(length("")); // 0
        p(length(text)); // 6

        p(text.toLowerCase()); // foobar
        p(lowerCase(text)); // foobar

        p(ordinalIndexOf(text, "o", 1)); // 1
        p(ordinalIndexOf(text, "o", 2)); // 2

        p(overlay(text, "haha", 3, 3)); // FoohahaBar

        p(remove(text, 'o')); // FBar
        p(remove(text, "oo")); // FBar

        p(removeEnd("appkr.dev/path", "/path")); // appkr.dev
        p(removeStart("https://appkr.dev", "https://")); // appkr.dev

        p(repeat('-', 10)); // ----------
        p(repeat("=-", 10)); // =-=-=-=-=-=-=-=-=-=-

        p(replace(text, "oo", "aa")); // FaaBar

        String[] search = {"a", "o"};
        String[] replace = {"b", "e"};
        p(replaceEach(text, search, replace)); // FeeBbr

        p(reverse(text)); // raBooF

        p(rightPad(text, 10)); // FooBar
        p(rightPad("123", 10, "0")); // 1230000000

        p(rotate(text, 3)); // BarFoo

        pa(split(text)); // {FooBar}
        pa(split("appkr.dev", '.')); // {appkr,dev}

        pa(splitByCharacterType("foo200Bar")); // {foo,200,B,ar}

        p(strip("   abc   ")); // abc

        p(substring(text, 3)); // Bar
        p(substring(text, 3, 5)); // Ba

        p(swapCase(text)); // fOObAR

        pi(toCodePoints(text)); // {70,111,111,66,97,114}
        pi(toCodePoints("대한민국")); // {45824,54620,48124,44397}

        byte[] koBytes = "대한민국".getBytes();
        p(toEncodedString(bytes, new US_ASCII())); // FooBar
        p(toEncodedString(koBytes, null)); // 대한민국

        p(wrap(text, "'")); // 'FooBar'
        p(wrapIfMissing(text, "F")); // FooBarF
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }

    private void pi(int[] i) {
        System.out.println(ArrayUtils.toString(i));
    }
}
