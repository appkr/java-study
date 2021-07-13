package dev.appkr.example.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

  @Id
  private Long id;
  private String addrCode;
  private String roadHash;
  private String roadAddr;
  private String sido;
  private String sigungu;
  private String emd;
  private String roadName;
  private String roadCode;
  private String isBasement;
  private String buildingNo;
  private String buildingName;
  private String legalHash;
  private String legalAddr;
  private String legalDong;
  private String legalCode;
  private String ri;
  private String isMountain;
  private String beonji;
  private String adminHash;
  private String adminAddr;
  private String adminDong;
  private String adminCode;
  private String zipCode;
  private String x;
  private String y;
  private Instant updatedAt;
}
