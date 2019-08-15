package kata;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class MutabilityTest {

    @Test
    public void isJavaMapImmutable() {
        // NOPE

        Map<String, String> source = new HashMap<>();
        source.put("foo", "bar");
        source.put("baz", "qux");

        Set<String> keys1 = source.keySet();
        Set<String> keys2 = source.keySet();

        source.remove("foo");

        assertTrue(1 == keys1.size());
        assertTrue( 1 == source.size());
    }

    @Test
    public void isJavaListImmutable() {
        // NOPE

        ArrayList<String> source = new ArrayList<>(Arrays.asList("foo", "bar"));

        String value = source.get(0);
        System.out.println(value); // foo

        source.set(0, "baz");

        assertEquals("baz", source.get(0));
    }

    @Test
    public void howToMapToBeImmutable() {
        Map<String, String> source = new HashMap<>();
        source.put("foo", "bar");
        source.put("baz", "qux");

        // Create new instance
        Set<String> keys1 = new HashSet<>(source.keySet());

        source.remove("foo");

        assertTrue(2 == keys1.size());
        assertTrue( 1 == source.size());
    }

    @Test
    public void howToListToBeImmutable() {
        ArrayList<String> source = new ArrayList<>(Arrays.asList("foo", "bar"));

        // Create new instance
        String value = new String(source.get(0));

        source.set(0, "baz");

        assertEquals("baz", source.get(0));
        assertEquals("foo", value);
    }
}
