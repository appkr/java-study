package dev.appkr.dynamodb.tuplePoc;

import static software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.appkr.dynamodb.model.ChangeLog;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Repository
@Slf4j
public class ChangeLogJsonRepository {

  static final String TABLE_NAME = "ChangeLog";

  final DynamoDbAsyncClient client;
  final ObjectMapper mapper;

  public ChangeLogJsonRepository(DynamoDbAsyncClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public Mono<ChangeLog> save(ChangeLog entity) {
    final Map<String, AttributeValue> item = Map.of(
        "key", fromS(entity.getKey()),
        "type", fromS(entity.getType()),
        "value", fromS(serialize(entity))
    );

    final CompletableFuture<ChangeLog> future = this.client
        .putItem(builder -> builder
            .tableName(TABLE_NAME)
            .item(item)
            .build())
        .handle((r, t) -> {
          if (t == null) {
            return entity;
          }
          log.error("저장 실패", t);
          return null;
        });

    return Mono.fromFuture(future);
  }

  public Mono<List<ChangeLog>> findAllByType(String type) {
    final CompletableFuture<List<ChangeLog>> future = this.client
        .scan(builder -> builder
            .tableName(TABLE_NAME)
            .indexName("type")
            .filterExpression("#type = :typeValue")
            .expressionAttributeNames(Map.of("#type", "type"))
            .expressionAttributeValues(Map.of(":typeValue", AttributeValue.fromS(type)))
            .build()
        )
        .thenApply(r -> r.items().stream()
            .map(item -> deserialize(item.get("value").s()))
            .toList()
        );

    return Mono.fromFuture(future);
  }

  public Mono<ChangeLog> delete(ChangeLog entity) {
    final CompletableFuture<ChangeLog> future = this.client
        .deleteItem(builder -> builder
            .tableName(TABLE_NAME)
            .key(Map.of("key", fromS(entity.getId())))
            .build())
        .handle((r, t) -> {
          if (t == null) {
            return entity;
          }
          log.error("삭제 실패", t);
          return null;
        });

    return Mono.fromFuture(future);
  }

  public Mono<Void> createTableIfNotExists() {
    final CompletableFuture<Void> future = this.client
        .describeTable(builder -> builder
            .tableName(TABLE_NAME)
            .build())
        .handle((r, t) -> {
          if (t != null) {
            this.client.createTable(builder -> builder
                    .tableName(TABLE_NAME)
                    .attributeDefinitions(
                        b -> b.attributeName("key").attributeType(ScalarAttributeType.S),
                        b -> b.attributeName("type").attributeType(ScalarAttributeType.S)
                    )
                    .keySchema(b -> b.attributeName("key").keyType(KeyType.HASH))
                    .globalSecondaryIndexes(b -> b
                        .indexName("type")
                        .keySchema(kb -> kb.attributeName("type").keyType(KeyType.HASH))
                        .provisionedThroughput(pb -> pb.readCapacityUnits(1L).writeCapacityUnits(1L))
                        .projection(pb -> pb.projectionType(ProjectionType.ALL))
                    )
                    .provisionedThroughput(b -> b.readCapacityUnits(1L).writeCapacityUnits(1L))
                )
                .join();
          }

          return null;
        });

    return Mono.fromFuture(future);
  }

  public Mono<Void> deleteTableIfExists() {
    final CompletableFuture<Void> future = this.client
        .deleteTable(builder -> builder
            .tableName(TABLE_NAME)
            .build())
        .handle((r, t) -> {
          if (t != null) {
            log.error("테이블 삭제 실패", t);
          }
          return null;
        });

    return Mono.fromFuture(future);
  }

  public String serialize(ChangeLog source) {
    String serialized = "";
    try {
      serialized = mapper.writeValueAsString(source);
    } catch (JsonProcessingException e) {
      log.warn("직렬화 실패", e);
    }

    return serialized;
  }

  public ChangeLog deserialize(String source) {
    ChangeLog deserialized = null;
    try {
      deserialized = mapper.readValue(source, ChangeLog.class);
    } catch (JsonProcessingException e) {
      log.warn("역직렬화 실패", e);
    }

    return deserialized;
  }
}
