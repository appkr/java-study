package kata.functionalinterfaces;

import java.time.Instant;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReferences {

    public static void main(String[] args) {
        st(s -> Integer.parseInt(s));
        st(Integer::parseInt);

        bound(i -> Instant.now().isAfter(i));
        bound(Instant.now()::isAfter);

        unbound(s -> s.toLowerCase());
        unbound(String::toLowerCase);

        constructor(() -> new TreeMap<>());
        constructor(TreeMap::new);

        array(i -> new int[i]);
        array(int[]::new);
    }

    public static void st(Function<String, Integer> function) { }

    public static void bound(Predicate<Instant> predicate) { }

    public static void unbound(UnaryOperator<String> operator) { }

    public static void constructor(Supplier<TreeMap<String, String>> supplier) { }

    public static void array(Function<Integer, int[]> function) { }
}
