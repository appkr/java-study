package com.example.liquibasedemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {

    }

    public Comment(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
