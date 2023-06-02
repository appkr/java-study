package dev.appkr.dynamodb;

import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CommentJpaRepository extends CrudRepository<Comment, String> {

  List<Comment> findAllByMentionIdOrderByCreatedAtAsc(Integer mentionId);
}
