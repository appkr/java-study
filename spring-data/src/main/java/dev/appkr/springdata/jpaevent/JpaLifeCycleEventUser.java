package dev.appkr.springdata.jpaevent;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Slf4j
public class JpaLifeCycleEventUser {

  @Id
  @GeneratedValue
  private int id;
  private String userName;
  private String firstName;
  private String lastName;
  @Transient
  private String fullName;

  @PrePersist
  public void prePersist() {
    log.info("prePersist {}", this);
  }

  @PostPersist
  public void postPersist() {
    log.info("postPersist {}", this);
  }

  @PreRemove
  public void preRemove() {
    log.info("preRemove {}", this);
  }

  @PostRemove
  public void postRemove() {
    log.info("postRemove {}", this);
  }

  @PreUpdate
  public void preUpdate() {
    log.info("preUpdate {}", this);
  }

  @PostUpdate
  public void postUpdate() {
    log.info("postUpdate {}", this);
  }

  @PostLoad
  public void postLoad() {
    this.fullName = firstName + " " + lastName;
    log.info("postLoad {}", this);
  }
}
