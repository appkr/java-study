package springstudy.pessimisticlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ArticleService {

    private ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Article findById(Long articleId) {
        try {
            Optional<Article> optional = repository.findById(articleId);
            Thread.sleep(1000);
            return optional.orElseThrow(RuntimeException::new);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Article> findByCategory(ArticleCategory category) {
        return repository.findByCategory(category);
    }

    public void updateArticle(Long articleId, String content) {
        Article article = findById(articleId);
        updateArticle(article, content);
    }

    public void updateArticle(Article article, String content) {
        try {
            log.info("Updating article#{}", article.getId());
            Thread.sleep(1000);
            article.setContent(content);
            repository.saveAndFlush(article);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAllArticlesIn(ArticleCategory category, String content) {
        List<Article> articles = findByCategory(category);
        articles.forEach(article -> {
            try {
                log.info("Updating article #{}", article.getId());
                Thread.sleep(1000);
                article.setContent(content);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void saveAndFlush(Article article) {
        repository.saveAndFlush(article);
    }
}
