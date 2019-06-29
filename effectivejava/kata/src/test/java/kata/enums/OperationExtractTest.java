package kata.enums;

import kata.enums.OperationExtract;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationExtractTest {

    @Test
    public void shouldAddNumber() {
        int res = OperationExtract.ADD.apply(1, 1);

        assertSame(2, res);
    }

    @Test
    public void shouldSubtractNumber() {
        int res = OperationExtract.SUBTRACT.apply(1, 1);

        assertSame(0, res);
    }

    @Test
    public void shouldMultiplyNumber() {
        int res = OperationExtract.MULTIPLY.apply(1, 1);

        assertSame(1, res);
    }

    @Test
    public void shouldDivideNumber() {
        int res = OperationExtract.DIVIDE.apply(1, 1);

        assertSame(1, res);
    }
}