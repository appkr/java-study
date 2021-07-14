package dev.appkr.example.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegionListDto {

  private List<? extends RegionDto> data;
  private PageDto page;
}
