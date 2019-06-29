package kata.enums;

import java.util.function.IntBinaryOperator;

public enum OperationLambda {

    ADD     ((x, y) -> x + y),
    SUBTRACT((x, y) -> x - y),
    MULTIPLY((x, y) -> x * y),
    DIVIDE  ((x, y) -> x / y);

    private final IntBinaryOperator operator;

    OperationLambda(IntBinaryOperator operator) {
        this.operator = operator;
    }

    public int apply(int x, int y) {
        return this.operator.applyAsInt(x, y);
    }
}
