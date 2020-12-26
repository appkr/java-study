package dev.appkr.springdata.objectdiff;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "singers")
@ToString(exclude = {"albums"})
@Getter
public class Singer implements Serializable, Copyable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @OneToMany(mappedBy = "singer")
  private Set<Album> albums = new HashSet<>();

  protected Singer() {
  }

  private Singer(String name) {
    this.name = name;
  }

  public static Singer of(String name) {
    return new Singer(name);
  }

  public void addAlbum(Album album) {
    if (!albums.contains(album)) {
      albums.add(album);
    }
  }

  public void changeName(String name) {
    this.name = name;
  }

  @Override
  public Singer copy() throws CloneNotSupportedException {
    Singer singer = (Singer) clone();
    return singer;
  }
}
