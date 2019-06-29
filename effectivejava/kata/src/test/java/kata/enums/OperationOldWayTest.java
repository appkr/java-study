package kata.enums;

import kata.enums.OperationOldWay;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationOldWayTest {

    @Test
    public void shouldCalculateNumber() {
        int res = OperationOldWay.ADD.apply(1, 1);

        assertSame(2, res);
    }
}