package dev.appkr.springdata.objectdiff;

import static org.junit.jupiter.api.Assertions.*;

import dev.appkr.springdata.objectdiff.Album;
import dev.appkr.springdata.objectdiff.AlbumRepository;
import dev.appkr.springdata.objectdiff.Singer;
import dev.appkr.springdata.objectdiff.SingerRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class JaversTest {

  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SingerRepository singerRepository;

  private Album album;

  @BeforeEach
  @Transactional
  public void setup() {
    Singer singer = Singer.of("김광석");
    singerRepository.save(singer);

    Album album = Album.of("다시 부르기");
    album.associateWithSinger(singer);
    this.album = albumRepository.save(album);
  }

  @DisplayName("given there is one album saved on the database"
      + " when query all"
      + " then the result size must be greater then zero")
  @Test
  @Transactional(readOnly = true)
  public void testBasicOperations() {
    final List<Album> all = albumRepository.findAll();
    assertTrue(all.size() > 0);
    log.info("all {}", all);
  }

  @DisplayName("given there is an album"
      + "when change title of the album"
      + "then javers should detect changes")
  @Test
  @Transactional
  public void testUpdateEntity() throws CloneNotSupportedException {
    final Album base = album.copy();
    album.changeTitle("다시 부르기 2");
    albumRepository.saveAndFlush(album);

    Javers javers = JaversBuilder.javers().build();
    Diff diff = javers.compare(base, album);
    log.info("changes {}", diff.getChanges());
  }
}
