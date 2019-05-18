## Learn Java 8 - Full Tutorial for Beginners / freeCodeCamp.org

https://www.youtube.com/watch?v=grEKMHGYyns

#### Code Structuring
- Book - Package
- Chapter - Class
- Paragraph - Method 

#### Abstraction
- prepareDinner()
  - prepareMainCourse()
    - boilWater()
    - putVegetable()
    - ...
  - prepareDesert()
    - ...

#### Code lines in a function
- 1~3 lines
- no more than 20 lines

#### Function parameters
- nothing is the best
- no more than 4

#### CQS
- 사이드 이펙트를 관리하는 방법

#### Function argument vs parameter

#### static Keyword
```java
public class Person {
    private static int numberOfPerson;
    public void Person() {
        numberOfPerson++;
    }
    public static int getNumberOfPerson() {
        return numberOfPerson;
    }
}

public class PersonTest {
    @Test
    public void shouldReturnNumberOfPerson() {
        Person p1 = new Person();
        Person p2 = new Person();
        assertEquals(2, Person.getNumberOfPerson());
    }
}
```

#### Primitive Types
```java
private byte myByte // default `0`, NOT USED NORMALLY
private short myShort // default `0`, NOT USED NORMALLY 
private int myint // default `0`
private long myLong // default `0`
private float myFloat // default `0.0`, PROBLEMATIC, Save 350 as an int instead of float 3.50 to denote $3.50
private double myDouble // default `0.0`, PROBLEMATIC, Same a above
private boolean myBoolean // default `false` only when it is an instance level
private char myChar // default `""`
private Object myObject // default `null` only when it is an instance level
```

```java
Byte.MIN_VALUE
Byte.MAX_VALUE
```
|Type|Ranges|
|---|--:|
|byte|-128 ~ 127|
|short|-32768 ~ 32767|
|int|-2147483648 ~ 2147483647|
|long|...|
|float|1.4E-45 ~ 3.4028235E38|
|double|...|
|char|0 ~ 65535|

#### Box Types/Wrapper Types/Object Types
- Default value of Wrapper types are always `null`
```java
Byte b1 = new Byte(myByte); Byte b1 = Byte.valueOf(myByte); // statis is better in terms of performance
...
Boolean b2 = Boolean.TRUE;
// boolean b2 = Boolean.TRUE; -> Automatic type casting occurrs
// boolean b2 = Boolean.TRUE.booleanValue(); -> But EXPLICIT is always better
Character c1 = Character.valueOf(myChar);
```

#### String
- `String`은 객체이므로, `==` 연산자로 비교할 수 없고, `equals()` 함수를 사용해야 함
```java
"Foo" == "Foo" // Wrong usage
"Foo".equals("Foo"); // Right usage
```

- 상수를 먼저 써야 `NullPointerException`을 피할 수 있음
```java
String myVar = "Foo";
"Foo".equals(myVar);
```

#### Array
```java
Person[] people = {
    new Person(), 
    new Person(), // comma after last item is allowed
};
int[] numbers = {1, 2};
```

#### Enum
- Before Jova 5 days
```java
private static final String[] LOG_LEVEL = {"DEBUG", "INFO", "NOTICE"};
public String[] getStates() {
    return Arrays.copyOf(LOG_LEVEL, LOG_LEVEL.length);
}
```

- From Java 5 on
```java
public enum LogLevel {
    DEBUG, INFO, NOTICE;
}
for (LogLevel level : LogLevel.values()) {
    // ...
}
```

- Enum with constructor & methods
```java 
public enum LogLevel {
    DEBUG(1), INFO(2), NOTICE(3);
    private int level;
    private LogLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
    public boolean isDebug() { // Good Practice
        return this == DEBUG;
    }
}
```

> 조건문에서 Enum을 사용할 때(꼭 Enum을 사용하지 않더라도) default 실행 로직에 예외를 던저라.
> 다른 개발자가 Enum에 새로운 값을 추가하면, 조건문에서 예상치 못한 결과를 초래할 수 있다.

#### Static Import
- Imports all static method of package
```java
import static org.junit.Assert.*;
```


#### Logging
- [slf4j](https://www.slf4j.org/manual.html) `org.slf4j.slf4j-api`
- logback `ch.qos.logback.logback-classic` `ch.qos.logback.logback-core`
```xml
<!-- // src/main/resources/logback.xml -->
<configuration debug="true">
  <appender>...</appender>
  <logger name "fully.qualified.package.path" level="DEBUG">
  <logger name "fully.qualified.another.path" level="ERROR">
  <root level="INFO">...</root>
</configuration>
```

![](https://www.slf4j.org/images/concrete-bindings.png)

```java
public class FooService {
    private final Logger logger = LoggerFactory.getLogger(FooService.class);
    public void doSomething() {
        if (logger.isDebugEnabled()) {
            logger.debug("message {}", binding);
        }
    }
}
```

- [Appenders](https://logback.qos.ch/manual/appenders.html) `OutputStreamAppender` `ConsoleAppender` `FileAppender` `RollingFileAppender` `SMTPAppender` `DBAppender` `SyslogAppender` `SiftingAppender` `AsyncAppender` 

#### Main
```java
public class FooMain {
    public static void main(String[] args) {}
    // Or with varargs, variable arguments (https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html#varargs)
    public static void main(String... args) {}
}
```

#### Exceptions Handling
- Checked Exception 함수 시그니처에 명시하지 않으면 컴파일 에러 발생. 해당 함수를 호출하는 함수 시그니처까지도 `throws` 구문을 모두 선언해야 함 e.g. `Exception`, `IOException`
- Unchecked Exception e.g. `RuntimeException`
- Checked Exception은 catch 한 후 Unchecked Exception으로 바꾸어 던질 수도 있다
- Validation 로직에서 예외를 던지고, 이를 핸들링하라
```java
public enum FooState {}

public class FooMain {
    public static void main(String[] args) {
        FooService fooSvc = new FooService();
        for (String arg : args) {
            if (isValid(arg)) {
                fooSvc.doSomething();
            } else {
                // Handle Exception
            }
        }
    }
    private static boolean isValid(String arg) {
        try {
            FooState.valueOf(arg);
        } catch (InvalidArgumentException e) {
            // Logging here
            return false;
        } finally {
            // Release resource if the resource is costly
        }
        return true;
    }
}
```

#### Interface
- Do not define CONSTANT in an interface

#### Inheritance
- Overriding needs the same signature
- 상위 클래스에 선언된 정적 메서드를 하위 클래스의 이름으로 호출해도 부모 클래스의 메서드를 호출하는 것이다
- @Override is a hint to the compiler that tells my intention of method overriding

#### Object#close()
- Must implements Cloneable, but it's just a shallow clone
- Deep copy vs shallow copy (non-primitive 속성도 복제하냐?)
- Static Constructor를 사용하는 것이 가장 안전


#### Immutable
```java
BigInteger one = new BigInteger("1");
BigInteger two = one.add(one);
BigInteger zero = two.substract(two);

assertTrue(one.intValue() == 1);
assertTrue(two.intValue() == 2);
assertTrue(zero.intValue() == 0);
```

#### Object
- hashCode()
- equals()

07:53:00
