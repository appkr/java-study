package dev.appkr.springdata.javer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "albums")
@ToString
public class Album implements Cloneable {

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
  protected Album clone() throws CloneNotSupportedException {
    return (Album) super.clone();
  }
}
