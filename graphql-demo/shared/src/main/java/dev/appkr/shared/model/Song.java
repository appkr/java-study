package dev.appkr.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Song {

  Long id;
  String title;
  Duration playTime;
  @JsonIgnore
  Album album;

  public Song(Long id, String title, Duration playTime) {
    this.id = id;
    this.title = title;
    this.playTime = playTime;
  }

  protected Song() {
  }

  public void associateAlbum(Album album) {
    this.album = album;
    album.addSong(this);
  }
}
