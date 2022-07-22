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

### Call backend services using WebClient

```shell
curl -s -XPOST -H 'Content-type: application/graphql+json' http://localhost:8080/graphql -d '{
"query": "{albums {id, title, publishedAt, singer {id, name} songs {id, title, playTime}, price}}"
}' | jq

{
  "data": {
    "albums": [
      {
        "id": "1",
        "title": "이문세 5집",
        "publishedAt": "1987-12-31T15:00:00Z",
        "singer": {
          "id": "1",
          "name": "이문세"
        },
        "songs": [
          {
            "id": "1",
            "title": "시를 위한 시",
            "playTime": "PT3M56S"
          },
          {
            "id": "2",
            "title": "안개꽃 추억으로",
            "playTime": "PT5M5S"
          }
        ],
        "price": 10000
      }
    ]
  }
}
```
