package dev.appkr.emexample;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "albums")
public class Album {

  @Id
  private Long id;
  private String title;
  private Instant createdAt;
  private Instant updatedAt;
  @ManyToOne
  private Singer singer;
}
