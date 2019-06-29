package kata.enums.jpa;

import java.util.Map;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Operation {

    ADD     ((x, y) -> x + y, "A"),
    SUBTRACT((x, y) -> x - y, "S"),
    MULTIPLY((x, y) -> x * y, "M"),
    DIVIDE  ((x, y) -> x / y, "D");

    private static final Map<String, Operation> VALUE_MAP = Stream.of(values())
        .collect(Collectors.toMap(o -> o.databaseValue, o -> o));

    private final IntBinaryOperator operator;

    private final String databaseValue;

    Operation(IntBinaryOperator operator, String databaseValue) {
        this.operator = operator;
        this.databaseValue = databaseValue;
    }

    public int apply(int x, int y) {
        return this.operator.applyAsInt(x, y);
    }

    public static Optional<Operation> fromString(String databaseValue) {
        return Optional.ofNullable(VALUE_MAP.get(databaseValue));
        // return VALUE_MAP.getOrDefault(databaseValue, Operation.ADD);
    }

    public String toDatabaseValue() {
        return this.databaseValue;
    }
}
