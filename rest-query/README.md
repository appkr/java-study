## REST Query Language

- [REST Query Language with Spring and JPA Criteria](https://www.baeldung.com/rest-search-language-spring-jpa-criteria)
```bash
curl -s -X POST http://localhost:8080/api/users -d '{"firstName": "John", "lastName": "Doe", "email": "john@example.com", "age": 20 }' -H 'content-type: application/json'

curl -s -X POST http://localhost:8080/api/users -d '{"firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 }' -H 'content-type: application/json'

curl -X GET http://localhost:8080/api/users?search=lastName%3ADoe%2Cage%3E25 | jq
[{"id": 2, "firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 } ]
```

- [REST Query Language with Spring Data JPA Specifications](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
```bash
curl -s -X POST http://localhost:8080/api/ex2/users -d '{"firstName": "John", "lastName": "Doe", "email": "john@example.com", "age": 20 }' -H 'content-type: application/json'

curl -s -X POST http://localhost:8080/api/ex2/users -d '{"firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 }' -H 'content-type: application/json'

curl -s -X GET http://localhost:8080/api/ex2/users?search=lastName%3ADoe%2Cage%3E25 | jq
```

- [REST Query Language with Spring Data JPA and Querydsl](https://www.baeldung.com/rest-api-search-language-spring-data-querydsl)
- [REST Query Language – Advanced Search Operations](https://www.baeldung.com/rest-api-query-search-language-more-operations)
```bash
curl -s -X POST http://localhost:8080/api/ex4/users -d '{"firstName": "John", "lastName": "Doe", "email": "john@example.com", "age": 20 }' -H 'content-type: application/json'

curl -s -X POST http://localhost:8080/api/ex4/users -d '{"firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 }' -H 'content-type: application/json'

curl -s -X GET http://localhost:8080/api/ex4/users?search=firstName%3Ajo%2A%2Cage%3C25 | jq
```

- [REST Query Language – Implementing OR Operation](https://www.baeldung.com/rest-api-query-search-or-operation)
- [REST Query Language with RSQL](https://www.baeldung.com/rest-api-search-language-rsql-fiql)
- [REST Query Language with Querydsl Web Support](https://www.baeldung.com/rest-api-search-querydsl-web-in-spring-data-jpa)
