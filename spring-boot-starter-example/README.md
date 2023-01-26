## spring-boot-starter-example

- official reference: https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-developing-auto-configuration.html
- Spring Boot Auto Configuration 설정과 원리: http://dveamer.github.io/backend/SpringBootAutoConfiguration.html
- SPRING BOOT AUTOCONFIGURE 구현해보기: https://seongmun-hong.github.io/springboot/Spring-boot-AutoConfigure

> **`NOTE`** spring-boot 3
> 
> Spring Boot 2.7 introduced a new META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports file for registering auto-configurations, while maintaining backwards compatibility with registration in spring.factories. With this release, support for registering auto-configurations in spring.factories has been removed in favor of the imports file.
> 
> @see https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#auto-configuration-files

### Run

in example-lib:
```shell
$ ./gradlew :example-lib:test
# hello library properties
```

in demo:
```shell
$ ./gradlew :demo:bootRun
# good day demo application properties
```

The result differs because we used `ExampleBean` implementation provided by the autoconfigure project and we defined a new value in demo project.
```java
// example-spring-boot-autoconfigure/src/main/java/dev/appkr/autoconfigure/ExampleAutoConfiguration.java
@Bean
@ConditionalOnMissingBean
ExampleBean exampleBean() {
  return () -> "good day";
}
```

```yaml
# demo/src/main/resources/application.yml
example:
  key: demo application properties
```

---

### Step-by-step guide

#### example-lib

Let's assume `dev.appkr.lib.Library` depends on two objects, one is `dev.appkr.lib.ExampleProperties` class and the other is `dev.appkr.lib.ExampleBean` interface.
```java
// example-lib/src/test/java/dev/appkr/lib/LibraryTest.java
final Library library = new Library(new ExampleBeanImpl(), new ExampleProperties());
library.someLibraryMethod();
```

#### demo

We want to use the library in a spring project, `demo`. And we want the two objects to be managed by the spring's `ApplicationContext`. In this scenario spring's auto configuration feature and spring-boot-starter provides a handy way to meet the needs.
```java
// demo/src/main/java/dev/appkr/demo/DemoApplication.java
@Bean
Library aLibrary(ExampleBean bean, ExampleProperties props) {
  return new Library(bean, props);
}

@Component
public class SpringComponent {
  // Spring injects ExampleBean and ExampleProperties to the Library
  // And the Library object is managed by spring's ApplicationContext
  @Autowired Library library;
}
```

#### example-spring-boot-autoconfigure

This project provides recipes how to construct `ExampleBean` and `ExampleProperties`. 

The `dev.appkr.autoconfigure.ExampleProperties` is just a POJO that extends `dev.appkr.lib.ExampleProperties`. But it has a spring-powered annotation `@ConfigurationProperties(prefix = "example")`, which means we want the object fields to be bound to the yaml object starting `example` key provided by `application.yml`  
```java
// example-spring-boot-autoconfigure/src/main/java/dev.appkr/autoconfigure/ExampleProperties.java
@ConfigurationProperties(prefix = "example")
public class ExampleProperties extends dev.appkr.lib.ExampleProperties {}
```

As usual, `@ConfigurationProperties` should be enabled by a configuration. In addition, we want to provide an implementation of `dev.appkr.lib.ExampleBean` as a `@Bean`. But it will be autoconfigured as long as the `demo` application does not provide its own `@Bean ExampleBean`.
```java
// example-spring-boot-autoconfigure/src/main/java/dev.appkr/autoconfigure/ExampleAutoConfiguration.java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  ExampleBean exampleBean() {
    return () -> "good day";
  }
}

// NOTE for spring-boot 3
@AutoConfiguration
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleAutoConfiguration {}
```

One thing to note is that, as the reference doc says, `src/main/resources/META-INF/spring.factories` must be provided, so that spring can hook up the `dev.appkr.autoconfigure.ExampleAutoConfiguration` file.
```shell
# example-spring-boot-autoconfigure/src/main/resources/META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
dev.appkr.autoconfigure.ExampleAutoConfiguration

# NOTE for spring-boot 3
# example-spring-boot-autoconfigure/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
dev.appkr.autoconfigure.ExampleAutoConfiguration
```

As it will be used in spring project, the following dependencies should be provided in `build.gradle`. By containing `spring-boot-configuration-processor`, `spring-configuration-metadata.json` will be generated automatically.
```groovy
// example-spring-boot-autoconfigure/build.gradle
dependencies {
    api project(':example-lib')

    annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
    annotationProcessor 'org.springframework.boot:spring-boot-autoconfigure-processor'
    api 'org.springframework.boot:spring-boot-starter'
}
```

#### example-spring-boot-starter

It's an empty project, just containing a link to `example-spring-boot-autoconfigure`.

```groovy
// example-spring-boot-starter/build.gradle
dependencies {
    api project(':example-spring-boot-autoconfigure')
}
```

---

### Publishing jars

```shell
$ ./gradlew publish
```

```diff
// demo/build.gradle
dependencies {
-    implementation project(':example-spring-boot-starter')
+    implementation 'dev.appkr:example-spring-boot-starter:0.0.1-SNAPSHOT'
}
```
