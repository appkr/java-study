package dev.appkr.springdata.javer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeLogTestController {

  private final AlbumRepository albumRepository;
  private final SingerRepository singerRepository;

  @PostMapping("/change-logs")
  @Transactional
  public void create() {
    Singer singer = Singer.of("김광석");
    singerRepository.save(singer);

    Album album = Album.of("다시 부르기");
    album.associateWithSinger(singer);
    albumRepository.save(album);
  }

  @GetMapping("/change-logs")
  @Transactional
  public void update() {
    final Optional<Album> optional = albumRepository.findById(1);
    optional.ifPresent(album -> {
      album.changeTitle("다시 부르기 3");
    });
  }
}
