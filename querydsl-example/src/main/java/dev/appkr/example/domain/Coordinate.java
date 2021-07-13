package dev.appkr.example.domain;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "coordinates")
@Data
public class Coordinate {

  @Id
  private Long id;
  private String legalcode;
  private String sido;
  private String sigungu;
  private String emd;
  private String roadCode;
  private String roadName;
  private String isBasement;
  private String mainNo;
  private String subNo;
  private String zipXode;
  private String addrCode;
  private String sigunguBuildingName;
  private String buildingType;
  private String adminCode;
  private String adminDong;
  private String floors;
  private String subFloors;
  private String isPublic;
  private String buildingCount;
  private String detailBuildingName;
  private String buildingNameChangeHistory;
  private String detailBuildingNameChangeHistory;
  private String hasHabitant;
  private String xCenter;
  private String yCenter;
  private String xFront;
  private String yFront;
  @Column(name = "wgs84_x_center")
  private String wgs84XCenter;
  @Column(name = "wgs84_y_center")
  private String wgs84YCenter;
  private String sidoEng;
  private String sigunguEng;
  private String emdEng;
  private String roadNameEng;
  private String isDong;
  private String changedReason;
  private Instant updatedAt;
}
