package javastudy.streams;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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
    public void streamOfArray() {
        String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};
        Arrays.stream(names)
            .filter(x -> x.startsWith("S"))
            .sorted()
            .forEach(System.out::println);
    }

    @Test
    public void average() {
        Arrays.stream(new int[] {2, 4, 6, 8, 10})
            .map (x -> x * x)
            .average()
            .ifPresent(System.out::println);
    }

    @Test
    public void streamFromList() {
        List<String> people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        people.stream()
            .map(String::toLowerCase)
            .filter(x -> x.startsWith("a"))
            .forEach(System.out::println);
    }

    @Test
    public void streamFromTextFile() throws IOException {
        final Stream<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"));
        bands
            .sorted()
            .filter(x -> x.length() > 13)
            .forEach(System.out::println);
        bands.close();
    }

    @Test
    public void collectResult() throws IOException {
        final Stream<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"));

        final List<String> results = bands
            .filter(x -> x.contains("jit"))
            .collect(toList());

        results.forEach(System.out::println);
    }

    @Test
    public void streamFromCsvFile() throws IOException {
        final Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));

        final long rowCount = rows
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .count();

        System.out.println(rowCount + " rows");
        rows.close();
    }

    @Test
    public void parseCsvData() throws IOException {
        final Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));

        rows
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .filter(x -> Integer.parseInt(x[1]) > 15)
            .forEach(x -> System.out.println(x[0] + " " + x[1] + " " + x[2]));

        rows.close();
    }

    @Test
    public void parseCsvDataAndMapIntoHashMap() throws IOException {
        final Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));

        final Map<String, Integer> map = rows
            .map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .filter(x -> Integer.parseInt(x[1]) > 15)
            .collect(toMap(x -> x[0], x -> Integer.parseInt(x[1])));

        rows.close();
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Test
    public void testReduce() {
        final Double sum = Stream.of(7.3, 1.5, 4.8)
            .reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println(sum);
    }

    @Test
    public void testSummaryStats() {
        final IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10)
            .summaryStatistics();
        System.out.println(summary);
    }
}
