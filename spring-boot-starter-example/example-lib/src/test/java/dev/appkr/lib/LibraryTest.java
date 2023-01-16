package dev.appkr.lib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LibraryTest {

  static final String DEFAULT_VALUE = "hello";

  @Test
  void someLibraryMethod() {
    final Library sut = new Library(new ExampleBeanImpl(), new ExampleProperties());
    final String expected = DEFAULT_VALUE + " " + ExampleProperties.DEFAULT_KEY;
    assertEquals(expected, sut.someLibraryMethod());
  }

  static class ExampleBeanImpl implements ExampleBean {

    @Override
    public String getValue() {
      return LibraryTest.DEFAULT_VALUE;
    }
  }
}
