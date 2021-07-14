package dev.appkr.example.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageDto {

  private Integer size = 10;
  private Long totalElements;
  private Integer totalPages;
  private Integer number = 1;
}
