package dev.appkr.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DynamoJpaTest {

  @Autowired AmazonDynamoDB amazonDynamoDB;
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired DynamoDBMapper dynamoDBMapper;
  @Autowired CommentJpaRepository repository;

  @BeforeEach
  void setup() {
    final CreateTableRequest req = dynamoDBMapper
        .generateCreateTableRequest(Comment.class)
        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

    req.getGlobalSecondaryIndexes()
        .forEach(idx -> idx
            .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
            .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
        );

    TableUtils.createTableIfNotExists(amazonDynamoDB, req);
  }

  @AfterEach
  void teardown() {
    DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(Comment.class);
    TableUtils.deleteTableIfExists(amazonDynamoDB, deleteTableRequest);
  }

  @Test
  void createItem() {
    final Comment entity = new Comment();
    entity.setName("foo");
    entity.setMentionId(1);
    entity.setContent("Hello dynamoDB");
    entity.setDeleted(false);
    entity.setCreatedAt(LocalDateTime.now());

    repository.save(entity);

    log.info("{}", entity);
  }

  @Test
  void findAll() {
    createItem();

    final Iterable<Comment> all = repository.findAll();

    log.info("{}", all.iterator().next());
  }

  @Test
  void updateItem() {
    createItem();

    final Iterable<Comment> all = repository.findAll();
    final Comment entity = all.iterator().next();
    entity.setName("bar");

    repository.save(entity);

    log.info("{}", entity);
  }

  @Test
  void deleteItem() {
    createItem();

    final Iterable<Comment> all = repository.findAll();
    final Comment entity = all.iterator().next();

    repository.delete(entity);

    final long count = repository.count();
    log.info("count={}", count);
  }
}
