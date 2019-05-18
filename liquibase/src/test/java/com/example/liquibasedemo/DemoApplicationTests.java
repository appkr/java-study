package com.example.liquibasedemo;

import com.example.liquibasedemo.domain.Article;
import com.example.liquibasedemo.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DemoApplicationTests {

    @Autowired
    private ArticleRepository repository;

    @Test
    public void providesFindOneWithOptional() {
        Article article = repository.save(
            new Article("Foo tile", "Bar content")
        );

        assertTrue(repository.findById(article.getId()).isPresent());
        assertFalse(repository.findById(article.getId() + 1).isPresent());
    }

    @Test
    public void auditingSetsJdk8DateTimeTypes() {
        Article article = repository.save(
            new Article("Foo tile", "Bar content")
        );

        assertNotNull(article.getCreatedAt());
    }

}
