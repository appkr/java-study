## Jackson Example

- [https://github.com/FasterXML/jackson-databind/](https://github.com/FasterXML/jackson-databind/)

![](idea-screenshot.png)

## Jackson Serialization Filtering

- @see https://www.baeldung.com/spring-security-role-filter-json

```bash
$ curl -s -H "Accept: application/json" http://admin:password@localhost:8080/items
  [{"id":1,"name":"Item 1","ownerName":"Frank"},{"id":2,"name":"Item 2","ownerName":"Bob"}]

$ curl -s -H "Accept: application/json" http://user:password@localhost:8080/items
  [{"id":1,"name":"Item 1"},{"id":2,"name":"Item 2"}]
```
