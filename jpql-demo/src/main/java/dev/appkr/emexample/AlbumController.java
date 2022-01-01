package dev.appkr.emexample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController {

  private final AlbumRepository repository;

  @GetMapping("/albums")
  public List<AlbumView> listAlbums(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
    return repository.findAll(page - 1, size);
  }
}
