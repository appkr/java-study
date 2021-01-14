package dev.appkr.springdata.envers2;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "audits")
@RevisionEntity(AuditTrailListener.class)
@Getter
@Setter
public class AuditableEntity extends DefaultRevisionEntity {

  private String updatedBy;
}
