package kata;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void shouldCreatePersonObjectWithBuilder() {
        Person sut = Person.builder("Strange", "Mr").build();
        assertTrue(sut instanceof Person);
    }
}