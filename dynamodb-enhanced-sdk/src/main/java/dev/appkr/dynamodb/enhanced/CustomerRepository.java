package dev.appkr.dynamodb.enhanced;

import dev.appkr.dynamodb.model.Customer;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Component
public class CustomerRepository {

  final DynamoDbAsyncTable<Customer> table;

  public CustomerRepository(DynamoDbEnhancedAsyncClient client) {
    this.table = client.table("Customer", Customer.getSchema());
  }

  public CompletableFuture<Customer> save(Customer item) {
    this.table.putItem(item);
    return CompletableFuture.completedFuture(item);
  }

  public SdkPublisher<Customer> findAll() {
    return this.table.scan().items();
  }

  public CompletableFuture<Customer> findById(String id) {
    return this.table.getItem(Key.builder().partitionValue(id).build());
  }

  public CompletableFuture<Customer> delete(Customer item) {
    return this.table.deleteItem(item);
  }

  public CompletableFuture<Void> createTableIfNotExists() {
    return this.table.describeTable()
        .handle((res, t) -> {
          if (t != null) {
            this.table.createTable();
          }
          return null;
        });
  }

  public CompletableFuture<Void> deleteTableIfExists() {
    return this.table.deleteTable();
  }
}
