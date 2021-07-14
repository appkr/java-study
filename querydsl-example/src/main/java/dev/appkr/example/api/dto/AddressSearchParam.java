package dev.appkr.example.api.dto;

import dev.appkr.example.domain.RegionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressSearchParam {

  private RegionType regionType;
  private String depth1;
  private String depth2;
  private String depth3;
  private String depth4;
  private String number;
}
