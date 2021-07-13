package dev.appkr.example.api.dto;

import dev.appkr.example.domain.RegionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionSearchParam {

  private RegionType regionType;
  private String depth1;
  private String depth2;
}
