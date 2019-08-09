package springstudy.querydsl;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * https://github.com/querydsl/querydsl/blob/master/querydsl-core/src/test/java/com/querydsl/core/types/dsl/ExpressionsTest.java
 */
public class ExpressionsTest {

    private static final StringPath str = Expressions.stringPath("str");
    private static final BooleanExpression a = Expressions.booleanPath("a");
    private static final BooleanExpression b = Expressions.booleanPath("b");

    @Test
    public void testAs() {
        assertEquals("s as str", Expressions.as(Expressions.stringPath("s"), str).toString());
    }

    @Test
    public void testAllOf() {
        assertEquals("a && b", Expressions.allOf(a, b).toString());
    }

    @Test
    public void testAnyOf() {
        assertEquals("a || b", Expressions.anyOf(a, b).toString());
    }

    @Test
    public void testConstant() {
        assertEquals("X", Expressions.constant("X").toString());
    }

    @Test
    public void testTemplate() {
        assertEquals("a && b", Expressions.template(Object.class, "{0} && {1}", a, b).toString());
    }

    @Test
    public void testComparableTemplate() {
        assertEquals("a && b", Expressions.comparableTemplate(Boolean.class, "{0} && {1}", a, b).toString());
    }

    @Test
    public void testNumberTemplate() {
        assertEquals("1", Expressions.numberTemplate(Integer.class, "1").toString());
    }

    @Test
    public void testStringTemplate() {
        assertEquals("X", Expressions.stringTemplate("X").toString());
    }

    @Test
    public void testBooleanTemplate() {
        assertEquals("a && b", Expressions.booleanTemplate("{0} && {1}", a, b).toString());
    }

    @Test
    public void testOperation() {
        assertEquals("a && b", Expressions.operation(Boolean.class, Ops.AND, a, b).toString());
    }

    @Test
    public void testPredicate() {
        assertEquals("a && b", Expressions.predicate(Ops.AND, a, b).toString());
    }

    @Test
    public void testStringPath() {
        assertEquals("variable.property",
            Expressions.stringPath(Expressions.path(Object.class, "variable"), "property").toString());
    }

    @Test
    public void testDateOperation() {
        assertEquals("current_timestamp()",
            Expressions.dateTimeOperation(Date.class, Ops.DateTimeOps.CURRENT_TIMESTAMP).toString());
    }

    @Test
    public void testTrueIsTrue() {
        assertEquals("true = true", Expressions.asBoolean(true).isTrue().toString());
    }

    @Test
    public void testOneIsOne() {
        assertEquals("1 = 1", Expressions.asComparable(1L).eq(1L).toString());
    }

    @Test
    public void testAsDate() {
        assertEquals("year(Thu Jan 01 09:00:00 KST 1970)", Expressions.asDate(new Date(1L)).year().toString());
    }

    @Test
    public void testAddNumber() {
        assertEquals("1 + 1", Expressions.asNumber(1L).add(Expressions.constant(1L)).toString());
    }

    @Test
    public void testAppend() {
        assertEquals("left + right",
            Expressions.asString("left").append(Expressions.constant("right")).toString());
    }
}
