## Gradle multi-module project

- main은 root 프로젝트에만
- module1, module2에 각각 선언한 RestController가 작동하는지 검증한다
- shared에 선언한 프로퍼티 및 설정 클래스를 root, module1, module2에서 참조할 수 있는지 검증한다

```
.
├── module1
│   ├── build.gradle
│   └── src
│       └── main
│           └── java
│               └── dev
│                   └── appkr
│                       └── example
│                           └── module1
│                               └── Module1Controller.java
├── module2
│   ├── build.gradle
│   └── src
│       └── main
│           └── java
│               └── dev
│                   └── appkr
│                       └── example
│                           └── module2
│                               └── Module2Controller.java
├── shared
│   ├── build.gradle
│   └── src
│       └── main
│           ├── java
│           │   └── dev
│           │       └── appkr
│           │           └── example
│           │               └── common
│           │                   └── config
│           │                       └── ApplicationProperties.java
│           └── resources
│               └── shared.yaml
└── src
    └── main
        ├── java
        │   └── dev
        │       └── appkr
        │           └── example
        │               └── Application.java
        └── resources
            └── application.yaml
```