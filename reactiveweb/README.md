## Spring Reactive Web

- [Static Content in Spring WebFlux](https://www.baeldung.com/spring-webflux-static-content) see /staticasset

- [Guide to Spring 5 WebFlux](https://www.baeldung.com/spring-webflux) see annotation
```bash
curl http://localhost:8080/employees

curl http://localhost:8080/employees/1

curl -L -X POST 'http://localhost:8080/employees/update' \
-H 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
-H 'Content-Type: application/json' \
-d '{
    "id": 1,
    "name": "Foo"
}'
```

- [Introduction to the Functional Web Framework in Spring 5](https://www.baeldung.com/spring-5-functional-web) see /functional
```bash
curl http://localhost:8080/functional/employees/

curl http://localhost:8080/functional/employees/1

curl -L -X POST 'http://localhost:8080/functional/employees/update' \
-H 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
-H 'Content-Type: application/json' \
-d '{
    "id": 1,
    "name": "Foo"
}'
```

- [Spring WebFlux Filters](https://www.baeldung.com/spring-webflux-filters) see /filter
```bash
curl http://localhost:8080/users/test

curl http://localhost:8080/players/test
```

- [Handling Errors in Spring WebFlux](https://www.baeldung.com/spring-webflux-errors) see /errorhandling
```bash
curl -s http://localhost:8080/hello\?name\=Foo

curl -s http://localhost:8080/hello
```

- WebClient see /webclient

### 토비의 봄 TV

- 5회 Reactive Streams (1) see /tobiep5
