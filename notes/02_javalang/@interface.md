#### @interface

The @ symbol denotes an annotation type definition.

That means it is not really an interface, but rather a new annotation type -- to be used as a function modifier, such as @override.

https://docs.oracle.com/javase/1.5.0/docs/guide/language/annotations.html

```java
import java.lang.annotation.*;

/**
 * Indicates that the annotated method is a test method.
 * This annotation should be used only on parameterless static methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test { }
```

```java
public class Foo {
    @Test public static void m1() { }
    public static void m2() { }
    @Test public static void m3() {
        throw new RuntimeException("Boom");
    }
    public static void m4() { }
    @Test public static void m5() { }
    public static void m6() { }
    @Test public static void m7() {
        throw new RuntimeException("Crash");
    }
    public static void m8() { }
}
```
```java
import java.lang.reflect.*;

public class RunTests {
   public static void main(String[] args) throws Exception {
      int passed = 0, failed = 0;
      for (Method m : Class.forName(args[0]).getMethods()) {
         if (m.isAnnotationPresent(Test.class)) {
            try {
               m.invoke(null);
               passed++;
            } catch (Throwable ex) {
               System.out.printf("Test %s failed: %s %n", m, ex.getCause());
               failed++;
            }
         }
      }
      System.out.printf("Passed: %d, Failed %d%n", passed, failed);
   }
}
```
```bash
$ java RunTests Foo
# Test public static void Foo.m3() failed: java.lang.RuntimeException: Boom 
# Test public static void Foo.m7() failed: java.lang.RuntimeException: Crash 
# Passed: 2, Failed 2
```
