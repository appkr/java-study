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

### Request-Response logging

Why correlationId is missing...?

```shell
2022-07-22 10:04:47.131  INFO [edge,,] 23979 --- [ctor-http-nio-2] edge                                     : Incoming Request: ffbfe223e15396d4
Remote: /127.0.0.1:53585
POST http://localhost:8080/graphql HTTP/1.1
Accept: */*
content-length: 102
Content-type: application/graphql+json
Host: localhost:8080
User-Agent: curl/7.79.1

{"query":"{albums {id, title, publishedAt, singer {id, name} songs {id, title, playTime}, price}}"}
```

```shell
2022-07-22 10:04:47.540  INFO [edge,9e1c7174eb7dfc82,9e1c7174eb7dfc82] 23979 --- [ctor-http-nio-2] edge                                     : Outgoing Response: ffbfe223e15396d4
Duration: 459 ms
HTTP/1.1 200 OK
Content-Length: 540
Content-Type: application/json

{"data":{"albums":[{"id":"1","title":"이문세 5집","publishedAt":"1987-12-31T15:00:00Z","singer":{"id":"1","name":"이문세"},"songs":[{"id":"1","title":"시를 위한 시","playTime":"PT3M56S"},{"id":"2","title":"안개꽃 추억으로","playTime":"PT5M5S"}],"price":10000},{"id":"2","title":"한바탕 웃음으로","publishedAt":"1989-04-09T15:00:00Z","singer":{"id":"2","name":"이선희"},"songs":[{"id":"3","title":"나의 거리","playTime":"PT3M40S"},{"id":"4","title":"오월의 햇살","playTime":"PT4M33S"}],"price":10000}]}}
```
