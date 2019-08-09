package springstudy.querydsl;

import com.querydsl.core.types.dsl.*;
import org.junit.Ignore;
import org.junit.Test;

import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * https://github.com/querydsl/querydsl/blob/master/querydsl-core/src/test/java/com/querydsl/core/types/dsl/CaseBuilderTest.java
 */
public class CaseBuilderTest {

    enum Gender {
        MALE, FEMALE;
    }

    static class Member {
        long money;
        long getMoney() {
            return money;
        }
    }

    @Test
    public void testCaseWhen() {
        SimpleExpression<Object> expr = new CaseBuilder()
            .when(Expressions.TRUE)
            .then(new Object())
            .otherwise(false);

        assertNotNull(expr);
    }

    @Test
    @Ignore
    public void testBooleanTyped() {
        /**
         * springstudy.querydsl.CaseBuilderTest > testBooleanTyped FAILED
         *     java.lang.NoClassDefFoundError at CaseBuilderTest.java:42
         *         Caused by: java.lang.ClassNotFoundException at CaseBuilderTest.java:42
         */
        Member member = alias(Member.class, "member");
        BooleanExpression cases = new CaseBuilder()
            .when($(member.getMoney()).gt(100))
            .then(true)
            .otherwise(false);

        assertEquals("case" +
                "when member.money > 100 then true" +
                "else false" +
                "end",
            cases.toString()
        );
    }

    @Test
    @Ignore
    public void testEnumTyped() {
        Member member = alias(Member.class, "member");
        EnumExpression<Gender> cases = new CaseBuilder()
            .when($(member.getMoney()).gt(100))
            .then(Gender.MALE)
            .otherwise(Gender.FEMALE);

        assertEquals("case" +
                "when member.money > 100 then MALE" +
                "else FEMALE" +
                "end",
            cases.toString()
        );
    }
}
