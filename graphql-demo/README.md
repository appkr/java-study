## GraphQL Demo

### Endpoints

| endpoint | description                          |
|----------|--------------------------------------|
| GraphQL  | http://localhost:8080/graphql        |
| Schema   | http://localhost:8080/graphql/schema |
| Web UI   | http://localhost:8080/graphiql       |

### Hello

```shell
curl -s -XPOST -H 'Content-type: application/graphql+json' http://localhost:8080/graphql -d '{
  "query": "{ hello }"
}' | jq
{
  "data": {
    "hello": "Hello GraphQL"
  }
}
```
