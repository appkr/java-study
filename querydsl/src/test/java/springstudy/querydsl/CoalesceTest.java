package springstudy.querydsl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://github.com/querydsl/querydsl/blob/master/querydsl-core/src/test/java/com/querydsl/core/types/dsl/CoalesceTest.java
 */
public class CoalesceTest {

    private final StringPath firstname = Expressions.stringPath("firstname");
    private final StringPath lastname = Expressions.stringPath("lastname");

    @Test
    public void testDsl() {
        assertEquals("coalesce(firstname, lastname)", firstname.coalesce(lastname).toString());
    }
}
