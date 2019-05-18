## Gradle

- http://kwonnam.pe.kr/wiki/gradle/task

#### Install
```bash
$ brew install gradle
$ gradle -v
$ gradle -h
$ gradle tasks
$ gradle dependencies
$ gradle test
```

#### Create Build Project
```bash
$ gradle init
$ tree
.
├── build.gradle # 빌드 스크립트
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle # 빌드 설정
```
```groovy
// build.gradle
task foo(type: Copy, group: "Custom", description: "Copies sources to the dest directory") {
    from "src"
    into "dest"
}
```
```bash
$ mkdir src && touch src/foo.txt
$ gradle foo
$ ls -al dest/foo.txt
# -rw-r--r--  1 appkr  staff  0  2 16 13:42 dest/foo.txt
```
```groovy
// build.gradle
plugins {
    id "base"
}

task bar(type: Zip, group: "Custom", description: "Archives sources in a zip file") {
    from "src"
    setArchiveName "basic-demo-1.0.zip"
}
```
```bash
$ gradle bar
$ ls -al build/distributions/basic-demo-1.0.zip
# -rw-r--r--  1 appkr  staff  114  2 16 13:46 build/distributions/basic-demo-1.0.zip
$ gradle tasks
# Custom tasks
# ------------
# bar - Archives sources in a zip file
# foo - Copies sources to the dest directory
```
```groovy
// build.gradle
description = "A trivial Gradle build"
version = "1.0"
```
```bash
$ gradle properties
$ gradle properties | grep version
# version: 1.0
```

#### Create Java Application
```bash
$ gradle init --type java-application
$ tree
.
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main # Default Java source folder
    │   └── java
    │       └── App.java
    └── test # Default Java test folder
        └── java
            └── AppTest.java
```
```groovy
// build.gradle
plugins {
    id 'java'
    id 'application'
}

mainClassName = 'App'

dependencies {
    compile 'com.google.guava:guava:23.0'
    testCompile 'junit:junit:4.12'
}

repositories {
    jcenter()
}

// MUST TO MAKE EXECUTABLE JAR
jar {
    manifest {
        attributes 'Main-Class': 'App'
    }
}
```
```bash
$ gradle build
# > Task :compileJava
# > Task :processResources NO-SOURCE
# > Task :classes
# > Task :jar
# > Task :startScripts
# > Task :distTar
# > Task :distZip
# > Task :assemble
# > Task :compileTestJava
# > Task :processTestResources NO-SOURCE
# > Task :testClasses
# > Task :test
# > Task :check
# > Task :build

$ tree
.
├── build
│   ├── classes
│   │   └── java
│   │       ├── main
│   │       │   └── App.class
│   │       └── test
│   │           └── AppTest.class
│   ├── distributions
│   │   ├── gradle-demo.tar
│   │   └── gradle-demo.zip
│   ├── libs
│   │   └── gradle-demo.jar
│   ├── reports # open build/reports/tests/test/index.html
│   ├── scripts
│   ├── test-results
│   └── tmp # 빌드 과정에 사용하는 임시 필도
├── ...
├── build.gradle
├── settings.gradle
└── src
    ├── main
    │   └── java
    │       └── App.java
    └── test
        └── java
            └── AppTest.java
```
```bash
$ java -jar build/libs/gradle-demo.jar
# OR
$ gradle run
```

#### Create SpringBoot Application
```bash
$ gradle init --type java-application
```
```groovy
// build.gradle
buildscript {
    ext {
        springBootVersion = '2.1.3.RELEASE'
    }
    repositories {
        mavenCentral()
        maven { url "http://repo.spring.io/plugins-release" }
        maven { url "https://plugins.gradle.org/m2/" }
    }
    dependencies {
        classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"
    }
}

plugins {
    id 'java'
    id "io.spring.dependency-management" version "1.0.6.RELEASE"
}

apply plugin: 'java'
sourceCompatibility = 1.8
targetCompatibility = 1.8
assert System.properties['java.specification.version'] == '1.8'

apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'
apply plugin: 'war'

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation "org.springframework.boot:spring-boot-dependencies:${springBootVersion}"
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

group = 'hello'
version = '0.0.1-SNAPSHOT'

bootJar {
    mainClassName = 'hello.App'
}

bootWar {
    mainClassName = 'hello.App'
}

springBoot {
    mainClassName = 'hello.App'
}

defaultTasks 'bootRun'
```
```java
package hello; // src/main/java/hello/App.java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```
```java
package hello; // src/main/java/hello/HelloGradleController.java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloGradleController {
    @GetMapping
    public String helloGradle() {
        return "Hello Gradle!";
    }
}
```
```java
package hello; // src/test/java/hello/AppTest.java

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class AppTest {
    @Autowired private MockMvc mvc;

    @Test public void helloGradle() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello Gradle!"));
    }
}
```
```bash
$ gradle bootJar
$ gradle bootRun
$ java -jar build/libs/spring-demo.jar
```
