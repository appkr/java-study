#### Builder Example

```java
import java.util.*;
import java.lang.*;
import java.io.*;

class Ideone
{
    public static void main (String[] args) throws java.lang.Exception
    {
        Person.Builder personBuilder = Person.builder();
        personBuilder.name("foo");
        System.out.println(personBuilder.create());
    }
}

class Person {

    private final String name;

    private Person(Person.Builder builder) {
        this.name = builder.name;
    }

    public static Person.Builder builder() {
        return new Person.Builder();
    }
    
    public String toString() {
        return this.name;
    }

    public static class Builder {

        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Person create() {
            return new Person( this );
        }
    }
}
```
