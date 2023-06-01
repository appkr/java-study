package dev.appkr.dynamodb;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@SpringBootTest
@Slf4j
public class DynamoSdkTest {

  @Autowired DynamoDbClient client;

  @Test
  void createTable() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_CreateTable.html
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

    final CreateTableResponse res = client.createTable(req);

    log.info("{}", res);
  }

  @Test
  void listTables() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_ListTables.html
    final ListTablesRequest req = ListTablesRequest.builder()
        .exclusiveStartTableName("Com")
        .limit(1)
        .build();

    final ListTablesResponse res = client.listTables(req);

    log.info("{}", res);
  }

  @Test
  void createItem() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_PutItem.html
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

    final PutItemResponse res = client.putItem(req);

    log.info("{}", res);
  }

  @Test
  void getItem() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_GetItem.html
    final GetItemRequest req = GetItemRequest.builder()
        .tableName("Comment")
        .key(Map.of("id", AttributeValue.fromS("1")))
        .build();

    final GetItemResponse res = client.getItem(req);

    log.info("{}", res);
  }

  @Test
  void updateItem() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_PutItem.html
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

    final PutItemResponse res = client.putItem(req);

    log.info("{}", res);
  }

  @Test
  void deleteItem() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_DeleteItem.html
    final DeleteItemRequest req = DeleteItemRequest.builder()
        .tableName("Comment")
        .key(Map.of("id", AttributeValue.fromS("1")))
        .build();

    final DeleteItemResponse res = client.deleteItem(req);

    log.info("{}", res);
  }

  @Test
  void deleteTable() {
    // https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_DeleteTable.html
    final DeleteTableRequest req = DeleteTableRequest.builder()
        .tableName("Comment")
        .build();

    final DeleteTableResponse res = client.deleteTable(req);

    log.info("{}", res);
  }
}
