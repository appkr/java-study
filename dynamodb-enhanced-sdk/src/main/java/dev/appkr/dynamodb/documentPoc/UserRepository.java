package dev.appkr.dynamodb.documentPoc;

import dev.appkr.dynamodb.model.User;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.*;

@Component
public class UserRepository {

  final DynamoDbAsyncTable<User> table;

  public UserRepository(DynamoDbEnhancedAsyncClient client) {
    this.table = client.table("User", TableSchema.fromBean(User.class));
    createTableIfNotExists().block();
  }

  public Mono<User> save(User item) {
    final PutItemEnhancedRequest<User> req = PutItemEnhancedRequest.builder(User.class).item(item).build();
    final CompletableFuture<User> future = this.table.putItemWithResponse(req)
        .thenApply(PutItemEnhancedResponse::attributes);
    return Mono.fromFuture(future);
  }

  public Flux<User> findAll() {
    final ScanEnhancedRequest req = ScanEnhancedRequest.builder().build();
    final PagePublisher<User> pagePublisher = this.table.scan(req);
    return Flux.from(pagePublisher).flatMapIterable(Page::items);
  }

  public Mono<User> findById(String id) {
    final GetItemEnhancedRequest req = GetItemEnhancedRequest.builder()
        .key(r -> r.partitionValue(id))
        .build();
    final CompletableFuture<User> future = this.table.getItem(req);
    return Mono.fromFuture(future);
  }

  public Mono<User> delete(User item) {
    final DeleteItemEnhancedRequest req = DeleteItemEnhancedRequest.builder()
        .key(r -> r.partitionValue(item.getId()))
        .build();
    final CompletableFuture<User> future = this.table.deleteItem(req);
    return Mono.fromFuture(future);
  }

  public Mono<Void> createTableIfNotExists() {
    final CompletableFuture<Void> future = this.table.describeTable()
        .handle((res, t) -> {
          if (t != null) {
            this.table.createTable(CreateTableEnhancedRequest.builder().build());
          }
          return null;
        });
    return Mono.fromFuture(future);
  }

  public Mono<Void> deleteTableIfExists() {
    return Mono.fromFuture(this.table.deleteTable());
  }
}
