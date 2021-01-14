package dev.appkr.springdata.envers2;

import org.hibernate.envers.RevisionListener;

public class AuditTrailListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    AuditableEntity entity = (AuditableEntity) revisionEntity;
    entity.setUpdatedBy("foo");
  }
}
