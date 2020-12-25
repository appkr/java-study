/**
 * Reference
 * @see https://github.com/Leibosite/museum-parent/blob/master/museum-agent/src/main/java/com/qingruan/museum/agent/modified/event/DataModifiedIntegrator.java
 */
package dev.appkr.springdata.objectdiff;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class DefaultIntegrator implements Integrator {

  @Override
  public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry) {
    EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);
    registry.appendListeners(EventType.POST_UPDATE, new DefaultPostUpdateEventListener());
  }

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    //
  }
}
