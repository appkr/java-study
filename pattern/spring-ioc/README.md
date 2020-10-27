## Spring IoC (Spring Bean)

- module 1 @see https://www.baeldung.com/spring-bean
> In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.

- module 2 @see https://www.baeldung.com/spring-getbean
```java
@Bean(name = {"tiger", "kitty"})
@Scope(value = "prototype")
Tiger getTiger(String name) {
  return new Tiger(name);
}

Tiger tiger = context.getBean(Tiger.class, "Shere Khan");
```

- module 3 @see https://www.baeldung.com/spring-factorybean

- module 4 @see https://www.baeldung.com/spring-beans-factory-methods

