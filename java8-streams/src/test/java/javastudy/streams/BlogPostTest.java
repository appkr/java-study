package javastudy.streams;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import javastudy.streams.BlogPost.BlogPostType;
import org.junit.Before;
import org.junit.Test;

public class BlogPostTest {

  List<BlogPost> fixtures = new ArrayList<>();

  @Before
  public void setUp() {
    fixtures = asList(new BlogPost("Java tutorial", "appkr", BlogPostType.GUIDE, 1),
        new BlogPost("PHP tutorial", "appkr", BlogPostType.GUIDE, 2),
        new BlogPost("TypeScript tutorial", "Martin", BlogPostType.GUIDE, 1),
        new BlogPost("Spring newsletter", "Josh Long", BlogPostType.NEWS, 1),
        new BlogPost("PHP rfc review", "Popov", BlogPostType.REVIEW, 1));
  }

  @Test
  public void groupByType() {
    Map<BlogPostType, Set<BlogPost>> byType = fixtures.stream()
        .collect(groupingBy(f -> f.getType(), toSet()));
    System.out.println(byType);
  }

  @Test
  public void groupByTuple() {
    Map<Tuple, List<BlogPost>> byType = fixtures.stream()
        .collect(groupingBy(f -> new Tuple(f.getType(), f.getAuthor())));
    System.out.println(byType);
  }

  @Test
  public void groupByAuthorAndType() {
    final Map<String, Map<BlogPostType, List<BlogPost>>> byAuthorAndType = fixtures.stream()
        .collect(groupingBy(BlogPost::getAuthor, groupingBy(BlogPost::getType)));
    System.out.println(byAuthorAndType);
  }

  @Test
  public void averageByType() {
    Map<BlogPostType, Double> averageByType = fixtures.stream()
        .collect(groupingBy(BlogPost::getType, averagingInt(BlogPost::getLikes)));
    System.out.println(averageByType);
  }

  @Test
  public void sumByType() {
    Map<BlogPostType, Integer> sumByType = fixtures.stream()
        .collect(groupingBy(BlogPost::getType, summingInt(BlogPost::getLikes)));
    System.out.println(sumByType);
  }

  @Test
  public void maxLikesPerType() {
    final Map<BlogPostType, Optional<BlogPost>> maxLikesPerType = fixtures.stream()
        .collect(groupingBy(BlogPost::getType, maxBy(comparingInt(BlogPost::getLikes))));
    System.out.println(maxLikesPerType);
  }

  @Test
  public void summarizeByType() {
    final Map<BlogPostType, IntSummaryStatistics> summarizeByType = fixtures.stream()
        .collect(groupingBy(BlogPost::getType, summarizingInt(BlogPost::getLikes)));
    System.out.println(summarizeByType);
  }

  @Test
  public void mapToOtherObject() {
    final Map<BlogPostType, String> mapToOtherObject = fixtures.stream()
        .collect(groupingBy(BlogPost::getType,
            mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));
    System.out.println(mapToOtherObject);
  }

  @Test
  public void groupingToConcurrentMapByType() {
    final ConcurrentMap<BlogPostType, List<BlogPost>> byType = fixtures.stream()
        .collect(groupingByConcurrent(BlogPost::getType));
    System.out.println(byType);
  }
}
