/**
 * Reference
 * @see https://okky.kr/article/363302
 * @see https://github.com/Leibosite/museum-parent/blob/master/museum-agent/src/main/java/com/qingruan/museum/agent/modified/event/PostUpdateEventListenerImpl.java
 */
package dev.appkr.springdata.javer;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultPostUpdateEventListener implements PostUpdateEventListener {

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    int[] dirtyProperties = event.getDirtyProperties();
    if (dirtyProperties == null || dirtyProperties.length == 0) {
      return;
    }

    Object entity = event.getEntity();
    Object[] newStates = event.getState().clone();
    Object[] oldStates = event.getOldState().clone();
    String[] propertyNames = event.getPersister().getPropertyNames();

    for (int index : dirtyProperties) {
      log.info("Entity {} property changed: \n"
          + "  property: {}\n"
          + "       old: {}\n"
          + "       new: {}"
      , entity.getClass(), propertyNames[index], oldStates[index], newStates[index]);
    }
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return true;
  }

  @Override
  public boolean requiresPostCommitHandling(EntityPersister persister) {
    return true;
  }
}
