package springstudy.pessimisticlock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleService service;

    @Test
    public void canRetrieveArticleByCategory() {
        List<Article> articles = service.findByCategory(ArticleCategory.CAT_A);
        assertEquals(4, articles.size());
    }

    @Test
    public void canUpdateArticle() throws InterruptedException {
        service.updateArticle(1L, "MODIFIED");
        Article article = service.findById(1L);
        log.info("{}", article);
        assertEquals("MODIFIED", article.getContent());
    }

    @Test // 업데이트 중에도 레코드를 읽을 수 있다
    public void shouldNotBlockReadOperationEvenWhenTheLockIsActive() throws InterruptedException {
        log.info("Test started");
        ExecutorService es = Executors.newFixedThreadPool(5);

        es.submit(() -> {
            log.info("Handling update request");
            service.updateArticle(1L, "MODIFIED");
            Article article = service.findById(1L);
            log.info("Handling update done {}", article);
        });

        for (int i = 0; i < 10 ; i++) {
            es.submit(() -> {
                log.info("Handling concurrent read requests");
                List<Article> articles = service.findByCategory(ArticleCategory.CAT_A);
            });
        }

        assertTrue(true);
        Thread.sleep(10000);
    }

    @Test
    public void shouldBlockWriteOperationWhenPreOccupiedLockIsActive() throws InterruptedException {
        log.info("Test started");
        service.updateAllArticlesIn(ArticleCategory.CAT_A, "MODIFIED");

        assertTrue(true);
        Thread.sleep(5000);
    }

    @Test // 같은 레코드에 대해 동시에 업데이트 트랜잭션이 발생하면, 잠금을 가장 먼저 얻은 트랜잭션만 성공한다. 이 테스트는 싱글 쓰레드에서 작동하므로 데드락이 발생하지 않음
    public void shouldBlockWriteOperationWhenPreOccupiedLockIsActive2() throws InterruptedException {
        log.info("Test started");
        ExecutorService es = Executors.newFixedThreadPool(3);

        service.updateArticle(1L, "MODIFIED");
        Article article1 = service.findById(1L);
        log.info("Handling update done {}", article1);

        service.updateArticle(1L, "MODIFIED");
        Article article2 = service.findById(1L);
        log.info("Handling update done {}", article2);

        service.updateArticle(1L, "MODIFIED");
        Article article3 = service.findById(1L);
        log.info("Handling update done {}", article3);

        Thread.sleep(10000);
    }

    @Test // 같은 레코드에 대해 동시에 업데이트 트랜잭션이 발생하면, 잠금을 가장 먼저 얻은 트랜잭션만 성공한다
    public void shouldBlockWriteOperationWhenPreOccupiedLockIsActive3() throws InterruptedException {
        log.info("Test started");
        ExecutorService es = Executors.newFixedThreadPool(3);

        es.submit(() -> {
            service.updateArticle(1L, "MODIFIED");
            Article article = service.findById(1L);
            log.info("Handling update done {}", article);
        });
        es.submit(() -> {
            service.updateArticle(1L, "MODIFIED");
            Article article = service.findById(1L);
            log.info("Handling update done {}", article);
        });
        es.submit(() -> {
            service.updateArticle(1L, "MODIFIED");
            Article article = service.findById(1L);
            log.info("Handling update done {}", article);
        });

        Thread.sleep(10000);
    }
}

/*2019-06-25 01:50:50.420  INFO 3665 --- [           main] s.pessimisticlock.ArticleServiceTest     : Test started
Hibernate:
    select
        article0_.id as id1_0_0_,
        article0_.category as category2_0_0_,
        article0_.content as content3_0_0_
    from
        articles article0_
    where
        article0_.id=? lock in share mode
Hibernate:
    select
        article0_.id as id1_0_0_,
        article0_.category as category2_0_0_,
        article0_.content as content3_0_0_
    from
        articles article0_
    where
        article0_.id=? lock in share mode
Hibernate:
    select
        article0_.id as id1_0_0_,
        article0_.category as category2_0_0_,
        article0_.content as content3_0_0_
    from
        articles article0_
    where
        article0_.id=? lock in share mode
2019-06-25 01:50:51.575  INFO 3665 --- [pool-1-thread-1] s.pessimisticlock.ArticleService         : Updating article#1
2019-06-25 01:50:51.575  INFO 3665 --- [pool-1-thread-2] s.pessimisticlock.ArticleService         : Updating article#1
2019-06-25 01:50:51.575  INFO 3665 --- [pool-1-thread-3] s.pessimisticlock.ArticleService         : Updating article#1
Hibernate:
    update
        articles
    set
        category=?,
        content=?
    where
        id=?
Hibernate:
    update
        articles
    set
        category=?,
        content=?
    where
        id=?
Hibernate:
    update
        articles
    set
        category=?,
        content=?
    where
        id=?
Hibernate:
    select
        article0_.id as id1_0_0_,
        article0_.category as category2_0_0_,
        article0_.content as content3_0_0_
    from
        articles article0_
    where
        article0_.id=? lock in share mode
2019-06-25 01:50:52.627  WARN 3665 --- [pool-1-thread-3] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
2019-06-25 01:50:52.627  WARN 3665 --- [pool-1-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
2019-06-25 01:50:52.627 ERROR 3665 --- [pool-1-thread-3] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
2019-06-25 01:50:52.627 ERROR 3665 --- [pool-1-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
2019-06-25 01:50:53.633  INFO 3665 --- [pool-1-thread-2] s.pessimisticlock.ArticleServiceTest     : Handling update done Article(id=1, content=MODIFIED, category=CAT_A)*/
