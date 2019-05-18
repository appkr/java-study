## Jhipster-generated codes should be changed

#### build.gradle
```groovy
// build.gradle
apply from: 'gradle/swagger.gradle'

// gradle/swagger.gradle
openApiGenerate {
    // ...
    apiPackage = "{packageHere}.api"
    modelPackage = "{packageHere}.api.model"
    configOptions = [
        useTags: "true",
        delegatePattern: "true",
        dateLibrary: "java8",
        java8: "true",
        hideGenerationTimestamp: "true"
    ]
}
```

#### package.json
```json
// package.json
{
  // ...
  "dependencies": {
    "npm": "^6.4.1",
    "redoc": "^2.0.0-alpha.41",
    "redoc-cli": "^0.7.0"
  }
}
```

#### Msa application dev env setup
Clone Uaa
```bash
$ git@github.com:meshkorea/vroong-uaa.git
$ cd vroong-uaa
~/vroong-uaa $ git checkout -b local
~/vroong-uaa $ curl https://raw.githubusercontent.com/appkr/java-spring-dev-ref/master/notes/82_uaa-application-dev.yml -o src/main/resources/config/application-dev.yml
~/vroong-uaa $ curl https://raw.githubusercontent.com/appkr/java-spring-dev-ref/master/notes/83_uaa-Dockerfile -o src/main/docker/Dockerfile
~/vroong-uaa $ curl https://raw.githubusercontent.com/appkr/java-spring-dev-ref/master/notes/84_uaa-entrypoint.sh -o src/main/docker/entrypoint.sh
```

Customize project
```bash
~/app $ curl https://raw.githubusercontent.com/appkr/java-spring-dev-ref/master/notes/81_local.yml -o src/main/docker/local.yml
~/app $ docker-compose -f src/main/docker/local.yml build
~/app $ docker-compose -f src/main/docker/local.yml up
```

(First time only) 
Docker-compose will make MySql data volume `~/vroongmsa/mysql_data/`on host, and will be mounted on the mysql container.
```mysql
-- Connect to localhost:3306

CREATE USER 'homestead'@'localhost' IDENTIFIED BY 'secret';
CREATE USER 'homestead'@'%' IDENTIFIED BY 'secret';

CREATE DATABASE uaa DEFAULT CHARACTER SET=utf8mb4 DEFAULT COLLATE=utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON uaa.* TO 'homestead'@'localhost';
GRANT ALL PRIVILEGES ON uaa.* TO 'homestead'@'%';
FLUSH PRIVILEGES;
```

#### Components

The JHipster Registry is a runtime application on which all applications registers and get their configuration from. It also provides runtime monitoring dashboards. 
- The JHipster Registry is a Netflix Eureka server, that provides service discovery for all applications.
- The JHipster Registry is a Spring Config Server

JHipster UAA is a JHipster-based User Authentication and Authorization system, which uses the OAuth2 protocol.
