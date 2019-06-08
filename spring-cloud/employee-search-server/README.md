## Employee Search Server

```bash
$ curl -s -X GET http://localhost:8080/employees/1 | jq
#{
#  "id": 1,
#  "name": "Foo"
#}
```