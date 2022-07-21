## GraphQL Demo

### Run the cluster

Download and build jhipster-uaa
```shell
~ $ wget https://github.com/appkr/msa-starter/raw/master/jhipster-uaa.zip && unzip jhipster-uaa.zip && cd jhipster-uaa

~/jhipster-uaa $ ./gradlew clean jibDockerBuild -Djib.to.image=jhipster-uaa -Djib.to.tags=latest
# If it fails because of docker hub auth, provide more option to the command
# ~/jhipster-uaa $ ... -Djib.from.auth.username={USERNAME} -Djib.from.auth.password={PASSWORD}
```

Run the cluster
```shell
~/graphql-demo $ ./gradlew composeUp
~/graphql-demo $ ./gradlew :edge:bootRun
~/graphql-demo $ ./gradlew :backend:bootRun
```

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
