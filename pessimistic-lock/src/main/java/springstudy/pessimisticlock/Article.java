package springstudy.pessimisticlock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "articles")
@Getter
@Setter
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "category", length = 40)
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;
}
