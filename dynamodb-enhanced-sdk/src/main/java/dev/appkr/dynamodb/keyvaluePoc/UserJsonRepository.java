package dev.appkr.dynamodb.keyvaluePoc;

import static software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.appkr.dynamodb.model.User;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

@Component
@Slf4j
public class UserJsonRepository {

  static final String TABLE_NAME = "User";

  final DynamoDbAsyncClient client;
  final ObjectMapper mapper;

  public UserJsonRepository(DynamoDbAsyncClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
    this.createTableIfNotExists().block();
  }

  public Mono<User> save(User entity) {
    final Map<String, AttributeValue> item = Map.of(
        "id", fromS(entity.getId()),
        "value", fromS(serialize(entity))
    );
    final CompletableFuture<User> future = this.client
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

  public Mono<List<User>> findAll() {
    final CompletableFuture<List<User>> future = this.client
        .scan(builder -> builder
            .tableName(TABLE_NAME)
            .build())
        .thenApply(r -> r.items().stream()
            .map(item -> deserialize(item.get("value").s()))
            .toList()
        );

    return Mono.fromFuture(future);
  }

  public Mono<User> findById(String id) {
    final CompletableFuture<User> future = this.client
        .getItem(builder -> builder
            .tableName(TABLE_NAME)
            .key(Map.of("id", fromS(id)))
            .build())
        .handle((r, t) -> {
          if (t == null && r.hasItem()) {
            return deserialize(r.item().get("value").s());
          }
          log.error("조회 실패", t);
          return null;
        });

    return Mono.fromFuture(future);
  }

  public Mono<User> delete(User entity) {
    final CompletableFuture<User> future = this.client
        .deleteItem(builder -> builder
            .tableName(TABLE_NAME)
            .key(Map.of("id", fromS(entity.getId())))
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
                .keySchema(b -> b.attributeName("id").keyType(KeyType.HASH))
                .attributeDefinitions(
                    b -> b.attributeName("id").attributeType(ScalarAttributeType.S).build(),
                    b -> b.attributeName("value").attributeType(ScalarAttributeType.S).build()
                ).build());
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

  public String serialize(User source) {
    String serialized = "";
    try {
      serialized = mapper.writeValueAsString(source);
    } catch (JsonProcessingException e) {
      log.warn("직렬화 실패", e);
    }

    return serialized;
  }

  public User deserialize(String source) {
    User deserialized = null;
    try {
      deserialized = mapper.readValue(source, User.class);
    } catch (JsonProcessingException e) {
      log.warn("역직렬화 실패", e);
    }

    return deserialized;
  }
}
