package dev.appkr.springdata.javer;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "singers")
@ToString(exclude = {"albums"})
public class Singer {

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
}
