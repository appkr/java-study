package springstudy.pessimisticlock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Article> findById(Long articleId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Article> findByCategory(ArticleCategory category);
}
