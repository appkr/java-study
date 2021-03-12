package javastudy.streams;

import javastudy.streams.BlogPost.BlogPostType;

public class Tuple {
  BlogPostType type;
  String author;

  public Tuple(BlogPostType type, String author) {
    this.type = type;
    this.author = author;
  }

  public Tuple() {
  }

  public BlogPostType getType() {
    return type;
  }

  public void setType(BlogPostType type) {
    this.type = type;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "Tuple{" +
        "type=" + type +
        ", author='" + author + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Tuple tuple = (Tuple) o;

    if (type != tuple.type) {
      return false;
    }
    return author != null ? author.equals(tuple.author) : tuple.author == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (author != null ? author.hashCode() : 0);
    return result;
  }
}
