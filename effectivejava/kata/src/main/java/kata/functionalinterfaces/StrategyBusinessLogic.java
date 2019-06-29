package kata.functionalinterfaces;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

public class StrategyBusinessLogic {

    private final IntUnaryOperator operator;

    private StrategyBusinessLogic(IntUnaryOperator operator) {
        this.operator = operator;
    }

    public static StrategyBusinessLogic of(IntUnaryOperator operator) {
        Objects.requireNonNull(operator);
        return new StrategyBusinessLogic(operator);
    }

    public void compute() {
        System.out.println("x");
        System.out.println(operator.applyAsInt(5));
        System.out.println("c");
        System.out.println("---");
    }
}
