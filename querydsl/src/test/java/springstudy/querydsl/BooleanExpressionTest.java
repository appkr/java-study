package springstudy.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://github.com/querydsl/querydsl/blob/master/querydsl-core/src/test/java/com/querydsl/core/types/dsl/BooleanExpressionTest.java
 */
public class BooleanExpressionTest {

    private final BooleanExpression a = Expressions.booleanPath("a");
    private final BooleanExpression b = Expressions.booleanPath("b");
    private final BooleanExpression c = Expressions.booleanPath("c");

    @Test
    public void testAnyOf() {
        assertEquals(a.or(b).or(c), Expressions.anyOf(a, b, c));
    }

    @Test
    public void testAllOf() {
        assertEquals(a.and(b).and(c), Expressions.allOf(a, b, c));
    }

    @Test
    public void testAndAnyOf() {
        assertEquals(a.and(b.or(c)), a.andAnyOf(b, c));
    }

    @Test
    public void testOrAllOf() {
        assertEquals(a.or(b.and(c)), a.orAllOf(b, c));
    }
}
