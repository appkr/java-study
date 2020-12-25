package dev.appkr.springdata.objectdiff;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
    final Optional<Album> optional = albumRepository.findById(albumId);
    optional.ifPresent(album -> {
      album.changeTitle(dto.getTitle());
    });
  }
}
