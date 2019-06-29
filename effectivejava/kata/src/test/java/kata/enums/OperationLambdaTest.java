package kata.enums;

import kata.enums.OperationLambda;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationLambdaTest {

    @Test
    public void shouldCalculateNumber() {
        int res = OperationLambda.ADD.apply(1, 1);

        assertSame(2, res);
    }

    @Test
    public void shouldSubtractNumber() {
        int res = OperationLambda.SUBTRACT.apply(1, 1);

        assertSame(0, res);
    }

    @Test
    public void shouldMultiplyNumber() {
        int res = OperationLambda.MULTIPLY.apply(1, 1);

        assertSame(1, res);
    }

    @Test
    public void shouldDivideNumber() {
        int res = OperationLambda.DIVIDE.apply(1, 1);

        assertSame(1, res);
    }
}