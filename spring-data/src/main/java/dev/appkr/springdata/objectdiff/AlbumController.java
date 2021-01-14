package dev.appkr.springdata.objectdiff;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

  private final AlbumRepository albumRepository;
  private final SingerRepository singerRepository;

  @PostMapping("/albums")
  @Transactional
  public void create() {
    Singer singer = Singer.of("김광석");
    singerRepository.save(singer);

    Album album = Album.of("다시 부르기");
    album.associateWithSinger(singer);
    albumRepository.save(album);
  }

  @PutMapping("/albums/{albumId}")
  @Transactional
  public void update(@PathVariable Integer albumId, @RequestBody AlbumDto dto) {
    albumRepository.findById(albumId).ifPresent(album -> {
      final ChangeLogCollector collector = new ChangeLogCollector(album);

      album.changeTitle(dto.getTitle());

      log.info("changeLogs {}", collector.collect());
      /**
       * [
       *   ChangeLog(
       *     changedAt=2020-12-26T06:24:07.147154Z,
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
