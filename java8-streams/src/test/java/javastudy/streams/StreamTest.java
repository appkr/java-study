package javastudy.streams;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void intStream() {
        IntStream
            .range(0, 10)
            .forEach(System.out::print);
    }

    @Test
    public void intStreamWithSkip() {
        IntStream
            .range(0, 10)
            .skip(5)
            .forEach(x -> System.out.print(x));
    }

    @Test
    public void intStreamWithSum() {
        final int sum = IntStream
            .range(1, 5)
            .sum();
        System.out.println(sum);
    }

    @Test
    public void streamOf() {
        Stream.of("Ava", "Aneri", "Alberto")
            .sorted()
            .findFirst()
            .ifPresent(System.out::println);
    }

    @Test
    public void () {
        
    }
}
