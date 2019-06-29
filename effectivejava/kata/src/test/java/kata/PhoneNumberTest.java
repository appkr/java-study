package kata;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PhoneNumberTest {

    // Factory Method

    @Test
    public void canCreatePhoneNumberWithFactoryMethod() {
        PhoneNumber sut = PhoneNumber.of(123, 4567);

        assertTrue(sut instanceof PhoneNumber);
    }

    // Builder Pattern

    @Test
    public void canCreatePhoneNumberWithBuilder() {
        PhoneNumber sut = PhoneNumber.builder()
            .areaCode(123)
            .number(4567)
            .build();

        assertTrue(sut instanceof PhoneNumber);
    }

    // Equals Spec

    @Test
    public void objectShouldBeReflective() {
        PhoneNumber sut = PhoneNumber.of(123, 1234);
        assertTrue(sut.equals(sut));
    }

    @Test
    public void objectShouldBeSymmetrical() {
        PhoneNumber sutA = PhoneNumber.of(123, 1234);
        PhoneNumber sutB = PhoneNumber.of(123, 1234);
        assertTrue(sutA.equals(sutB));
        assertTrue(sutB.equals(sutA));
    }

    @Test
    public void objectShouldBeTransitive() {
        PhoneNumber sutA = PhoneNumber.of(123, 1234);
        PhoneNumber sutB = PhoneNumber.of(123, 1234);
        PhoneNumber sutC = PhoneNumber.of(123, 1234);
        assertTrue(sutA.equals(sutB));
        assertTrue(sutB.equals(sutC));
        assertTrue(sutA.equals(sutC));
    }

    @Test
    public void objectShouldBeConsistent() {
        // Hydrated by JPA object must be the same as the original
        PhoneNumber sutA = PhoneNumber.of(123, 1234);
        PhoneNumber sutB = PhoneNumber.of(123, 1234);
        assertTrue(sutA.equals(sutB));
        assertTrue(sutA.equals(sutB));
        assertTrue(sutA.equals(sutB));
        assertTrue(sutA.equals(sutB));
    }

    @Test
    public void objectShouldBeNonNullity() {
        PhoneNumber sut = PhoneNumber.of(123, 1234);
        assertFalse(sut.equals(null));
    }

    // toString

    @Test
    public void shouldPrintObject() {
        PhoneNumber sut = PhoneNumber.of(123, 1234);
        System.out.println(sut);
    }

    // Formattable

    @Test
    public void shouldPrintFormattedObject() {
        PhoneNumber sut = PhoneNumber.of(123, 1234);
        System.out.printf("%s", sut);
    }

    // Comparator

    @Test
    public void shouldSortObject() {
        PhoneNumber sutA = PhoneNumber.of(123, 1234);
        PhoneNumber sutB = PhoneNumber.of(124, 1235);
        PhoneNumber sutC = PhoneNumber.of(124, 1234);
        List<PhoneNumber> list = new ArrayList<>(Arrays.asList(sutA, sutB, sutC));
        Collections.sort(list);
        System.out.println(list);
    }
}