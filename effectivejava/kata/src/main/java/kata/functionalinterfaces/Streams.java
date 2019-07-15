package kata.functionalinterfaces;

import java.util.function.*;
import java.util.stream.Stream;

public class Streams {

    public static final String[] strings =
        { "a", "7", "4", "z", "T", "c", "10", "h", "2" };

    public static void main(String[] args) {
//        UnaryOperator, Function
//        Stream.iterate(0, i -> i + 1);

//        BinaryOperator, BiFunction
        Integer res1 = Stream.of(1, 2, 3, 4)
            .reduce((x, y) -> x + y)
            .orElse(0);
        System.out.println(res1);

//        Predicate, Function, Consumer
        Stream.of(strings)
            .filter(s -> s.matches("\\d+"))
            .map(Integer::parseInt)
            .forEach(System.out::println);

//        Supplier
        Integer res2 = Stream.of(1, 2, 3, 4)
            .reduce((x, y) -> x + y)
            .orElseGet(() -> 0);
        System.out.println(res2);
    }
}
