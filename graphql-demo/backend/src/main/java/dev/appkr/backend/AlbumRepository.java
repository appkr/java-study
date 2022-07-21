package dev.appkr.backend;

import dev.appkr.shared.model.Album;
import dev.appkr.shared.model.Singer;
import dev.appkr.shared.model.Song;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AlbumRepository {

  static final Set<Album> all = new HashSet<>();
  static {
    final Singer msLee = new Singer(1L, "이문세");

    final Album msLee5th = new Album(1L, "이문세 5집", Instant.parse("1988-01-01T00:00:00+09:00"));
    msLee5th.setSinger(msLee);

    final Song song1 = new Song(1L, "시를 위한 시", Duration.parse("PT3M56S"));
    final Song song2 = new Song(2L, "안개꽃 추억으로", Duration.parse("PT5M05S"));
    song1.associateAlbum(msLee5th);
    song2.associateAlbum(msLee5th);

    final Singer shLee = new Singer(2L, "이선희");

    final Album shLee5th = new Album(2L, "한바탕 웃음으로", Instant.parse("1989-04-10T00:00:00+09:00"));
    shLee5th.setSinger(shLee);

    final Song song3 = new Song(3L, "나의 거리", Duration.parse("PT3M40S"));
    final Song song4 = new Song(4L, "오월의 햇살", Duration.parse("PT4M33S"));
    song3.associateAlbum(shLee5th);
    song4.associateAlbum(shLee5th);

    all.add(msLee5th);
    all.add(shLee5th);
  }

  public Set<Album> findAll() {
    return all;
  }

  public Optional<Album> findById(Long id) {
    return all.stream()
        .filter(album -> album.getId() == id)
        .findFirst();
  }
}
