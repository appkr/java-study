package dev.appkr.springdata.jpaevent;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class AuditTrailListener {

  @PrePersist
  @PreUpdate
  @PreRemove
  public void beforeAnyUpdate(Identifiable identifiable) {
    if (identifiable.getId() == 0) {
      log.info("[AUDIT] about to add an object");
    } else {
      log.info("[AUDIT] about to update or delete an object {}", identifiable.getId());
    }
  }

  @PostPersist
  @PostUpdate
  @PostRemove
  public void afterAnyUpdate(Identifiable identifiable) {
    log.info("[AUDIT] add/update/delete complete for object {}", identifiable.getId());
  }

  @PostLoad
  private void afterLoad(Identifiable identifiable) {
    log.info("[AUDIT] object {} loaded from database", identifiable.getId());
  }
}
