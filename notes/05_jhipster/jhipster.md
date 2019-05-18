## Jhipster
- https://www.jhipster.tech/

#### Install
```bash
$ brew install jhipster
$ jhipster --version
$ jhipster --help
```

#### Scaffolding App
```bash
# Scaffolding application
$ jhipster --skip-client
```
```bash
# Import JDL
$ jhipster import-jdl {path/to/jdl.jh}
```
```bash
# Add new entity
$ jhipster entity {EntityName}

# Add new controller
$ jhipster spring-controller {EntityName}

# Add new service
$ jhipster spring-service {EntityName}
```

#### Function Performance
```java
@Timed
public void saveAccount(@RequestBody UserDto userDto) {
    userService.updateUserInformation(userDTO);
}
```

