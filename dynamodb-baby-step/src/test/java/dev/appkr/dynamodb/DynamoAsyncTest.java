package dev.appkr.dynamodb;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@SpringBootTest
@Slf4j
public class DynamoAsyncTest {

  @Autowired DynamoDbAsyncClient client;

  @Test
  void createTable() {
    @SuppressWarnings("unchecked")
    final CreateTableRequest req = CreateTableRequest.builder()
        .tableName("Comment")
        .attributeDefinitions(
            // WARN: DO NOT CHAIN builder; WHICH IS NOT WORKING
            b -> b.attributeName("id").attributeType(ScalarAttributeType.S),
            b -> b.attributeName("mentionId").attributeType(ScalarAttributeType.N),
            b -> b.attributeName("createdAt").attributeType(ScalarAttributeType.S))
        .keySchema(b -> b.attributeName("id").keyType(KeyType.HASH))
        .globalSecondaryIndexes(b -> b
            .indexName("byMentionId")
            .keySchema(
                b1 -> b1.attributeName("mentionId").keyType(KeyType.HASH),
                b1 -> b1.attributeName("createdAt").keyType(KeyType.RANGE))
            .projection(b2 -> b2.projectionType(ProjectionType.ALL))
            .provisionedThroughput(b3 -> b3.readCapacityUnits(1L).writeCapacityUnits(1L)))
        .provisionedThroughput(builder -> builder.readCapacityUnits(1L).writeCapacityUnits(1L))
        .build();

    final CompletableFuture<CreateTableResponse> future = client.createTable(req);

    log.info("{}", future.join());
  }

  @Test
  void listTables() {
    final ListTablesRequest req = ListTablesRequest.builder()
        .exclusiveStartTableName("Com")
        .limit(1)
        .build();

    final CompletableFuture<ListTablesResponse> future = client.listTables(req);

    log.info("{}", future.join());
  }

  @Test
  void createItem() {
    final PutItemRequest req = PutItemRequest.builder()
        .tableName("Comment")
        .item(Map.of(
            "id", AttributeValue.fromS("1"),
            "name", AttributeValue.fromS("foo"),
            "mentionId", AttributeValue.fromN("1"),
            "content", AttributeValue.fromS("Hello Comment"),
            "deleted", AttributeValue.fromBool(false),
            "createdAt", AttributeValue.fromS(LocalDateTime.now().toString())
        ))
        .build();

    final CompletableFuture<PutItemResponse> future = client.putItem(req);

    log.info("{}", future.join());
  }

  @Test
  void getItem() {
    final GetItemRequest req = GetItemRequest.builder()
        .tableName("Comment")
        .key(Map.of("id", AttributeValue.fromS("1")))
        .build();

    final CompletableFuture<GetItemResponse> future = client.getItem(req);

    log.info("{}", future.join());
  }

  @Test
  void updateItem() {
    final PutItemRequest req = PutItemRequest.builder()
        .tableName("Comment")
        .item(Map.of(
            "id", AttributeValue.fromS("1"),
            "name", AttributeValue.fromS("bar"),
            "mentionId", AttributeValue.fromN("1"),
            "content", AttributeValue.fromS("Hello Comment"),
            "deleted", AttributeValue.fromBool(false),
            "createdAt", AttributeValue.fromS(LocalDateTime.now().toString())
        ))
        .build();

    final CompletableFuture<PutItemResponse> future = client.putItem(req);

    log.info("{}", future.join());
  }

  @Test
  void deleteItem() {
    final DeleteItemRequest req = DeleteItemRequest.builder()
        .tableName("Comment")
        .key(Map.of("id", AttributeValue.fromS("1")))
        .build();

    final CompletableFuture<DeleteItemResponse> future = client.deleteItem(req);

    log.info("{}", future.join());
  }

  @Test
  void deleteTable() {
    final DeleteTableRequest req = DeleteTableRequest.builder()
        .tableName("Comment")
        .build();

    final CompletableFuture<DeleteTableResponse> future = client.deleteTable(req);

    log.info("{}", future.join());
  }
}
