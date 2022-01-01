package dev.appkr.emexample;

import lombok.Data;

@Data
public class AlbumView {

  private Long id;
  private String title;
  private String singerName;

  public AlbumView(Album album, Singer singer) {
    this.id = album.getId();
    this.title = album.getTitle();
    this.singerName = singer.getName();
  }
}
