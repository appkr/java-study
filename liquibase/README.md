## Spring-Liquibase integration Demo

- 리퀴베이스는 데이터베이스 스키마의 변경사항을 추적하고 관리하기 위한 도구입니다. 
- `@Entity`와 데이터베이스를 비교해서 Diff를 구할 수 있습니다(Diff를 구하지 않고, 직접 쓰는 것을 추천합니다).
- `src/main/resources/liquibase/changelog-master.xml`에 등록된 DDL을 애플리케이션 실행 시점에 실행합니다. 
- https://medium.com/@harittweets/evolving-your-database-using-spring-boot-and-liquibase-844fcd7931da

#### 스프링 부트 프로젝트 생성
```bash
$ spring init -d=web,jpa,mysql --build=gradle liquibase-demo
```

#### 리퀴베이스 설치 및 설정
```groovy
// build.gradle
plugins {
    //...
    id 'org.liquibase.gradle' version '2.0.3'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'mysql:mysql-connector-java'
    implementation 'org.liquibase:liquibase-core:3.8.9'

    liquibaseRuntime 'mysql:mysql-connector-java'
    liquibaseRuntime 'org.liquibase.ext:liquibase-hibernate5:3.6'
    liquibaseRuntime sourceSets.main.compileClasspath
//    liquibaseRuntime sourceSets.main.output

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'com.h2database:h2'
}

liquibase {
    activities {
        main {
            url 'jdbc:mysql://localhost:3306/liquibasedemo?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC'
            username 'homestead'
            password 'secret'
            changeLogFile 'src/main/resources/liquibase/changelog-master.xml'
            referenceUrl 'hibernate:spring:com.example.liquibasedemo.domain?dialect=org.hibernate.dialect.MySQL5InnoDBDialect&amp;hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy&amp;hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy'
            referenceDriver 'liquibase.ext.hibernate.database.connection.HibernateDriver'
            defaultSchemaName 'liquibasedemo'
            logLevel 'debug'
            classpath 'src/main/resources/'
        }
    }

    runList = 'main'
}
```
```yml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/liquibasedemo?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: homestead
    password: secret
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        jdbc:
          time_zone: UTC

  liquibase:
    enabled: true
    change-log: classpath:/liquibase/changelog-master.xml
    contexts: dev
```

#### 최초 실행
```bash
$ ./gradlew clean bootRun
```

애플리케이션을 한번 실행하고 나면, 리퀴베이스가 이력 관리를 위해 만든 두 개의 테이블이 보입니다.
```sql
mysql> use liquibasedemo;
mysql> show tables;
/*
Tables_in_liquibasedemo
DATABASECHANGELOG
DATABASECHANGELOGLOCK
*/
```

#### Diff 파일 구하기
Entity를 작성하거나 변경하고, 아래 gradle 태스크를 실행하면, `src/main/resources/db/changelog/`에 변경할 DB 스키마를 담은 파일이 만들어집니다.
```bash
# 경우에 따라서는, 변경된 Entity를 반영하도록 빌드를 해야 하는 경우도 있었습니다.
$ ./gradlew clean build
$ ./gradlew diffChangeLog
```

실험해본 결과 아주 정확하지는 않습니다. 쓰레기 값들이 생길 경우도 잦습니다. 따라서, 내용이 정확한지 꼭 확인해야 합니다. 검토가 끝나면, `db/changelog/db.changelog-master.xml`에 생성된 파일 경로를 `<include ...>` 태그에 등록합니다. 
```xml
<?xml version="1.0" encoding="UTF-8"?>

<databaseChangeLog
  xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">

  <include file="/db/changelog/20190224000100_create_articles_comments_tags_tables.xml"/>
  <include file="/db/changelog/20190224000200_seed_dummy_data_on_each_table.xml"/>

</databaseChangeLog>
```

#### Diff 적용하기
```bash
$ ./gradlew clean bootRun
```

```sql
show tables;
/*
Tables_in_liquibasedemo
DATABASECHANGELOG
DATABASECHANGELOGLOCK
article_tag
articles
comments
tags
*/
```

#### 컬럼 코멘트

```xml
<column name="COL_NAME" type="varchar(255)" remarks="COMMENT HERE">
    <constraints nullable="false" />
</column>
```
