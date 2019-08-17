package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Locale;

import static org.apache.commons.lang3.LocaleUtils.*;

public class LocaleUtilsTest {

    @Test
    public void test() {
        p(toLocale("ko_KR").getDisplayName()); // 한국어 (대한민국)
        p(new Locale("ko", "KR").getDisplayName()); // 한국어 (대한민국)

        pa(localeLookupList(toLocale("ko_KR")).toArray()); // {ko_KR,ko}

        pa(availableLocaleList().toArray());
        // {,ar_AE,ar_JO,..}

        pa(availableLocaleSet().toArray());
        // {,ar_AE,ar_JO,..}

        p(isAvailableLocale(new Locale("ko"))); // true

        p(languagesByCountry("KR")); // [ko_KR]

        p(countriesByLanguage("ko")); // [ko_KR]
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }
}
