package dev.appkr.springdata.objectdiff;

import lombok.extern.slf4j.Slf4j;
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
public class EntityDiffTest {

  @Autowired private SingerRepository singerRepository;
  @Autowired private AlbumRepository albumRepository;
  private Album album;

  @BeforeEach
  public void setup() {
    Singer singer = Singer.of("김광석");
    singerRepository.save(singer);

    Album album = Album.of("다시 부르기");
    album.associateWithSinger(singer);
    this.album = albumRepository.save(album);
  }

  @DisplayName("given there is one album saved on the database"
      + " when the album is changed"
      + " then the changelog should be collected")
  @Test
  @Transactional
  public void testCollectChangeLog() {
    albumRepository.findById(album.getId()).ifPresent(album -> {
      final ChangeLogCollector collector = new ChangeLogCollector(album);

      album.changeTitle("다시 부르기 2");
      album.getSinger().changeName("Various artists");
      albumRepository.save(album);

      log.info("changeLogs {}", collector.collect());
      /**
       * [
       *   ChangeLog(
       *     changedAt=2020-12-26T05:48:44.400841Z,
       *     changedBy=00000000-0000-0000-0000-000000000000,
       *     changedField=singer.name,
       *     beforeValue=김광석,
       *     afterValue=Various artists,
       *     memo=singer.name changed from 김광석 to Various artists
       *   ),
       *   ChangeLog(
       *     changedAt=2020-12-26T05:48:44.400907Z,
       *     changedBy=00000000-0000-0000-0000-000000000000,
       *     changedField=title,
       *     beforeValue=다시 부르기,
       *     afterValue=다시 부르기 2,
       *     memo=title changed from 다시 부르기 to 다시 부르기 2
       *   )
       * ]
       */
    });
  }
}
