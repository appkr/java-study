package dev.appkr.example.api.dto;

import lombok.Data;

@Data
public class RoadDto extends RegionDto {

  private String sido;
  private String sigungu;
  private String emd;
  private String roadName;
  private String roadCode;
}
