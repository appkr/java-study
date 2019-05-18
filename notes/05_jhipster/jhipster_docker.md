## Jhipster Docker

#### Build
```bash
$ ./mvnw package -Pprod verify jib:dockerBuild
$ ./gradlew -Pdevelopment bootWar jibDockerBuild

# gradle -h
# -P, --project-prop        Set project property for the build script (e.g. -Pmyprop=myvalue).
```

#### Run
```bash
$ docker-compose -f src/main/docker/app.yml up
```
