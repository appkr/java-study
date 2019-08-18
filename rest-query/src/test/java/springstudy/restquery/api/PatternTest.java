package springstudy.restquery.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import springstudy.restquery.infra.SearchOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    private static final Pattern pattern;
    static {
        String operationSetExpr = StringUtils.join(SearchOperation.SIMPLE_OPERATION_SET, "|");
        pattern = Pattern.compile("(\\w+)(" + operationSetExpr + ")(\\*?)(\\w+)(\\*?),");
    }

    @Test
    public void testPattern() {
        String search = "firstName:jo*,age<25";
        Matcher m = pattern.matcher(search + ",");

        while(m.find()) {
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
            System.out.println(m.group(5));
        }
    }
}
