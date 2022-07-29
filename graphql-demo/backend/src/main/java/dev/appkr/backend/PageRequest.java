package dev.appkr.backend;

import lombok.Getter;

@Getter
public class PageRequest {
  private Integer page;
  private Integer size;

  private PageRequest(Integer page, Integer size) {
    this.page = page;
    this.size = size;
  }

  public static PageRequest of(Integer page, Integer size) {
    return new PageRequest(page, size);
  }
}
