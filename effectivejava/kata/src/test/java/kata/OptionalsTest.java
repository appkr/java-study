package kata;

import org.junit.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class OptionalsTest {

    private static Name obj = Name.EMPTY;

    @Test
    public void testOf() {
        Optional<Name> optional = Optional.of(obj);
        optional.ifPresent(o -> assertEquals(obj, o));
    }

    @Test
    public void testOfNullable() {
        Optional<Name> optional = Optional.ofNullable(obj);
        optional.ifPresent(o -> {
            System.out.println(o);
            assertEquals(obj, o);
        });
    }

    @Test
    public void testOfEmpty() {
        Optional<Name> optional = Optional.empty();
        optional.ifPresent(o -> {
            // Must not reach here
            System.out.println(o);
            assertFalse(true);
        });
    }

    @Test
    public void testIsPresent() {
        Optional<Name> optional = Optional.empty();
        if (optional.isPresent()) {
            // Must not reach here
            System.out.println(optional.get());
            assertFalse(true);
        }
    }

    @Test
    public void testFilter() {
        Set<Optional<Name>> names = new HashSet<>();
        names.add(Optional.of(Name.of("foo")));
        names.add(Optional.of(Name.of("bar")));
        names.forEach(n -> {
            n.filter(m -> {
                    return m.getValue().equalsIgnoreCase("foo");
                })
                .ifPresent(System.out::println);
        });
    }

    @Test
    public void testMap() {
        Set<Optional<Name>> names = new HashSet<>();
        names.add(Optional.of(Name.of("foo")));
        names.add(Optional.of(Name.of("bar")));
        names.forEach(n -> {
            n.map(m -> {
                    return m.getValue().toUpperCase();
                })
                .ifPresent(System.out::println);
        });
    }

    @Test
    public void testFlatMap() {
        Set<Optional<Name>> names = new HashSet<>();
        names.add(Optional.of(Name.of("foo")));
        names.add(Optional.of(Name.of("bar")));
        names.forEach(n -> {
            n.flatMap(m -> {
                    return Optional.of(m.getValue().toUpperCase());
                })
                .ifPresent(System.out::println);
        });
    }

    @Test
    public void testOrElse() {
        Optional<Name> optional = Optional.ofNullable(null);
        Name name = optional.orElse(Name.EMPTY);
        System.out.println(name);
    }

    @Test
    public void testOrElseGet() {
        Optional<Name> optional = Optional.ofNullable(null);
        Name name = optional.orElseGet(() -> Name.EMPTY);
        System.out.println(name);
    }

    @Test(expected = NoSuchElementException.class)
    public void testOrElseThrow() {
        Optional<Name> optional = Optional.ofNullable(null);
        Name name = optional.orElseThrow(NoSuchElementException::new);
    }
}