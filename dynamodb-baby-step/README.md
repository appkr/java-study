## DynamoDB baby step

- Reference: https://techblog.woowahan.com/2633/

## Run dynamodb locally

```shell
docker pull amazon/dynamodb-local
docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -inMemory -sharedDb
```

### Using AWS CLI

Create a table

```shell
aws dynamodb create-table \
  --endpoint-url http://localhost:8000 \
  --table-name Comment \
  --attribute-definitions AttributeName=id,AttributeType=S AttributeName=mentionId,AttributeType=N AttributeName=createdAt,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
  --global-secondary-indexes '[
  {
    "IndexName":"byMentionId",
    "KeySchema":[
      {
        "AttributeName":"mentionId",
        "KeyType":"HASH"
      },
      {
        "AttributeName":"createdAt",
        "KeyType":"RANGE"
      }
    ],
    "Projection":{
      "ProjectionType":"ALL"
    },
    "ProvisionedThroughput":{
      "ReadCapacityUnits":1,
      "WriteCapacityUnits":1
    }
  }
]'
```

List tables

```shell
aws dynamodb list-tables --endpoint-url http://localhost:8000
```

Write an item

```shell
aws dynamodb put-item \
  --endpoint-url http://localhost:8000 \
  --table-name Comment \
  --item '{
  "id":{"S":"1"},
  "name":{"S":"foo"},
  "mentionId":{"N":"1"},
  "content":{"S":"Hello comment"},
  "deleted":{"BOOL":false},
  "createdAt":{"S":"2023-05-29T00:00:00+09:00"}
}'
```

Read an item

```shell
aws dynamodb get-item \
  --endpoint-url http://localhost:8000 \
  --table-name Comment \
  --key '{"id":{"S":"1"}}'
```

Modify an item

```shell
aws dynamodb put-item \
  --endpoint-url http://localhost:8000 \
  --table-name Comment \
  --item '{
  "id":{"S":"1"},
  "name":{"S":"bar"},
  "mentionId":{"N":"1"},
  "content":{"S":"Hello comment"},
  "deleted":{"BOOL":false},
  "createdAt":{"S":"2023-05-29T00:00:00+09:00"}
}'
```

Delete an item

```shell
aws dynamodb delete-item \
  --endpoint-url http://localhost:8000 \
  --table-name Comment \
  --key '{"id":{"S":"1"}}'
```

Drop a table

```shell
aws dynamodb delete-table \
  --endpoint-url http://localhost:8000 \
  --table-name Comment
```

### Using Curl

Create a table

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.CreateTable' \
  -d '{
  "TableName": "Comment",
  "KeySchema": [
    {
      "AttributeName": "id",
      "KeyType": "HASH"
    }
  ],
  "AttributeDefinitions": [
    {
      "AttributeName": "id",
      "AttributeType": "S"
    },
    {
      "AttributeName": "mentionId",
      "AttributeType": "N"
    },
    {
      "AttributeName": "createdAt",
      "AttributeType": "S"
    }
  ],
  "GlobalSecondaryIndexes": [
    {
      "IndexName": "byMentionId",
      "KeySchema": [
        {
          "AttributeName": "mentionId",
          "KeyType": "HASH"
        },
        {
          "AttributeName": "createdAt",
          "KeyType": "RANGE"
        }
      ],
      "Projection": {
        "ProjectionType": "ALL"
      },
      "ProvisionedThroughput": {
        "ReadCapacityUnits": 1,
        "WriteCapacityUnits": 1
      }
    }
  ],
  "ProvisionedThroughput": {
    "ReadCapacityUnits": 1,
    "WriteCapacityUnits": 1
  }
}'
```

List tables

```shell
curl http://localhost:8000/ \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.ListTables' \
  -d '{
  "ExclusiveStartTableName": "Com",
  "Limit": 3
}'
```

Write an item

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.PutItem' \
  -d '{
  "TableName": "Comment",
  "Item": {
    "id":{"S":"1"},
    "name":{"S":"foo"},
    "mentionId":{"N":"1"},
    "content":{"S":"Hello comment"},
    "deleted":{"BOOL":false},
    "createdAt":{"S":"2023-05-29T00:00:00+09:00"}
  }
}'
```

Read an item

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.GetItem' \
  -d '{
  "TableName": "Comment",
  "Key": {"id":{"S":"1"}}
}'
```

Modify an item

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.PutItem' \
  -d '{
  "TableName": "Comment",
  "Item": {
    "id":{"S":"1"},
    "name":{"S":"bar"},
    "mentionId":{"N":"1"},
    "content":{"S":"Hello comment"},
    "deleted":{"BOOL":false},
    "createdAt":{"S":"2023-05-29T00:00:00+09:00"}
  }
}'
```

Delete an item

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.DeleteItem' \
  -d '{
  "TableName": "Comment",
  "Key": {"id":{"S":"1"}}
}'
```

Drop a table

```shell
curl http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key/secret/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.DeleteTable' \
  -d '{
  "TableName": "Comment"
}'
```

### Using AWS DynamoDb Java SDK

- [src/test/java/dev/appkr/dynamodb/DynamoSdkTest.java](src/test/java/dev/appkr/dynamodb/DynamoSdkTest.java)

### Using spring-data-dynamodb

Note that the library uses old version of AWS SDK, which is different from the library that we used above.

- [src/main/java/dev/appkr/dynamodb/DynamoJpaConfiguration.java](src/main/java/dev/appkr/dynamodb/DynamoJpaConfiguration.java)
- [src/main/java/dev/appkr/dynamodb/Comment.java](src/main/java/dev/appkr/dynamodb/Comment.java)
- [src/main/java/dev/appkr/dynamodb/CommentJpaRepository.java](src/main/java/dev/appkr/dynamodb/CommentRepository.java)
- [src/test/java/dev/appkr/dynamodb/DynamoJpaTest.java](src/test/java/dev/appkr/dynamodb/DynamoJpaTest.java)
