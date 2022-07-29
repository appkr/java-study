package dev.appkr.backend;

import dev.appkr.shared.model.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;

@SpringBootApplication
@RestController
@Slf4j
public class BackendApplication {

  AlbumRepository repository;

  public BackendApplication(AlbumRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/api/albums")
  public ResponseEntity<Set<Album>> listAlbums(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
    log.info("log test at backend");
    final PageRequest pageable = PageRequest.of(page, size);

    return ResponseEntity.ok(repository.findAll(pageable));
  }

  @GetMapping("/api/albums/{albumId}")
  public ResponseEntity<Album> getAlbum(@PathVariable("albumId") Long albumId) {
    final Album album = repository.findById(albumId)
        .orElseThrow(() -> new NoSuchElementException());

    return ResponseEntity.ok(album);
  }

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }
}
