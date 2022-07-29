package dev.appkr.backend;

import dev.appkr.shared.model.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
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
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
      final Jwt jwt = (Jwt) authentication.getPrincipal();
      log.info("username: {}", jwt.getClaim("user_name").toString());
    }
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
