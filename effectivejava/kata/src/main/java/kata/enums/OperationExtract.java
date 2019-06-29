package kata.enums;

import java.util.function.IntBinaryOperator;

public enum OperationExtract {

    ADD     (OpExAddition::apply),
    SUBTRACT(OpExSubtract::apply),
    MULTIPLY(OpExMultiply::apply),
    DIVIDE  (OpExDivide::apply);

    private final IntBinaryOperator operator;

    OperationExtract(IntBinaryOperator operator) {
        this.operator = operator;
    }

    public int apply(int x, int y) {
        return this.operator.applyAsInt(x, y);
    }
}
