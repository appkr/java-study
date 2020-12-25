package dev.appkr.springdata.objectdiff;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class PostUpdateEventListenerTest {

  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SingerRepository singerRepository;

  private Album album;

  @BeforeEach
  public void setup() {
    Singer singer = Singer.of("김광석");
    singerRepository.save(singer);

    Album album = Album.of("다시 부르기");
    album.associateWithSinger(singer);
    this.album = albumRepository.save(album);
  }

  @DisplayName("given there is an album"
      + "when change title of the album"
      + "then event listener should detect changes")
  @Test
  public void testUpdateEntity() throws CloneNotSupportedException {
    final Album base = album.clone();
    album.changeTitle("다시 부르기 2");
    albumRepository.saveAndFlush(album);
  }
}
