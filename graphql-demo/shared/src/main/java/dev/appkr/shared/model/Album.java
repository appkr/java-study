package dev.appkr.shared.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Album {

  Long id;
  String title;
  Instant publishedAt;
  Singer singer;
  Set<Song> songs = new HashSet<>();

  public Album(Long id, String title, Instant publishedAt) {
    this.id = id;
    this.title = title;
    this.publishedAt = publishedAt;
  }

  protected Album() {
  }

  public void setSinger(Singer singer) {
    this.singer = singer;
  }

  public void addSong(Song song) {
    this.songs.add(song);
  }
}
