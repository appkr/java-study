## DynamoDB enhanced sdk

- Reference: https://docs.aws.amazon.com/ko_kr/sdk-for-java/latest/developer-guide/examples-dynamodb-enhanced.html

## Run dynamodb locally

```shell
docker pull amazon/dynamodb-local
docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -inMemory -sharedDb
```

### Using AWS DynamoDbAsync Java SDK

- Configuration: [src/main/java/dev/appkr/dynamodb/DynamoDbConfiguration.java](src/main/java/dev/appkr/dynamodb/DynamoDbConfiguration.java)
- Model: [src/main/java/dev/appkr/dynamodb/Customer.java](src/main/java/dev/appkr/dynamodb/Customer.java)
- Repository: [src/main/java/dev/appkr/dynamodb/CustomerRepository.java](src/main/java/dev/appkr/dynamodb/CustomerRepository.java)
- Usage: [src/test/java/dev/appkr/dynamodb/DynamoEnhancedSdkTest.java](src/main/java/dev/appkr/dynamodb/DynamoEnhancedSdkTest.java)
