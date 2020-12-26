package dev.appkr.springdata.objectdiff;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import java.util.Optional;
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
      Album base = null;
      try {
        base = album.clone();
      } catch (CloneNotSupportedException e) {}
      album.changeTitle(dto.getTitle());

      DiffNode diff = ObjectDifferBuilder.buildDefault().compare(album, base);
      log.info("diff {}", diff);
    });
  }
}
