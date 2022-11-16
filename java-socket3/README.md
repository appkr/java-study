## netty-spring-example

- Gradle port of https://github.com/zbum/netty-spring-example
- To test if a TCP server and a HTTP server can run at the same time

### Run

```shell
./gradlew bootRun
```

### Test

```shell
# in a terminal session
telnet localhost 9080
login A
```

```shell
# in another terminal session
telnet localhost 9080
login B
A::hello?
```
