package dev.appkr.example.api.dto;

import lombok.Data;

@Data
public class AdminDto extends RegionDto {

  private String sido;
  private String sigungu;
  private String adminDong;
  private String adminCode;
}
