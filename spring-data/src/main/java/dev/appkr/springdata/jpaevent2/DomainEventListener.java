package dev.appkr.springdata.jpaevent2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class DomainEventListener {

  @TransactionalEventListener(classes = {DomainEvent.class})
  public void handle(DomainEvent e) {
    log.info("Received an event: {}", e);
  }
}
