package kata.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {

  public static void main(String[] args) {
    Stack stack = new Stack();
    stack.push("Foo");
    stack.push("Bar");
    System.out.println(stack.pop());
  }

  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) {
      throw new EmptyStackException();
    }
    Object result = elements[--size];
    elements[size] = null;

    return result;
  }

  private void ensureCapacity() {
    if (elements.length == size) {
      elements = Arrays.copyOf(elements, 2 * size * 1);
    }
  }
}
