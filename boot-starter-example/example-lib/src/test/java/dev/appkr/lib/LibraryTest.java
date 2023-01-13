package dev.appkr.lib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LibraryTest {

  @Test
  void someLibraryMethod() {
    final Library sut = new Library(new ExampleProperties());
    assertEquals(ExampleProperties.DEFAULT_KEY, sut.someLibraryMethod());
  }
}
