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
