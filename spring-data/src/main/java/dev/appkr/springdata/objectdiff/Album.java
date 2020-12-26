package dev.appkr.springdata.objectdiff;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "albums")
@ToString
@Getter
public class Album implements Copyable, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  @ManyToOne
  private Singer singer;

  protected Album() {
  }

  private Album(String title) {
    this.title = title;
  }

  public static Album of (String title) {
    return new Album(title);
  }

  public void associateWithSinger(Singer singer) {
    this.singer = singer;
    singer.addAlbum(this);
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeSinger(Singer singer) {
    this.singer = singer;
  }

  @Override
  public Album copy() throws CloneNotSupportedException {
    Album album = (Album) clone();
    album.singer = album.singer.copy();
    return album;
  }
}
