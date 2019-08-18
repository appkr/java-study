## REST Query Language

- [REST Query Language with Spring and JPA Criteria](https://www.baeldung.com/rest-search-language-spring-jpa-criteria)
```
curl -s -X POST http://localhost:8080/api/users -d '{"firstName": "John", "lastName": "Doe", "email": "john@example.com", "age": 20 }'

curl -s -X POST http://localhost:8080/api/users -d '{"firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 }'

curl -X GET http://localhost:8080/api/users?search=lastName:Doe,age>25
[{"id": 2, "firstName": "Tom", "lastName": "Doe", "email": "tom@example.com", "age": 25 } ]
```

- [REST Query Language with Spring Data JPA Specifications](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
- [REST Query Language with Spring Data JPA and Querydsl](https://www.baeldung.com/rest-api-search-language-spring-data-querydsl)
- [REST Query Language – Advanced Search Operations](https://www.baeldung.com/rest-api-query-search-language-more-operations)
- [REST Query Language – Implementing OR Operation](https://www.baeldung.com/rest-api-query-search-or-operation)
- [REST Query Language with RSQL](https://www.baeldung.com/rest-api-search-language-rsql-fiql)
- [REST Query Language with Querydsl Web Support](https://www.baeldung.com/rest-api-search-querydsl-web-in-spring-data-jpa)
