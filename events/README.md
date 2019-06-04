## Spring Events

- https://www.baeldung.com/spring-events
- https://github.com/eugenp/tutorials/tree/master/spring-all/src/main/java/org/baeldung/springevents/synchronous

#### To send and receive event
- the event should extend ApplicationEvent
- the publisher should inject an ApplicationEventPublisher object
- ~~the listener should implement the ApplicationListener interface~~
- the listener method should have `@EventListener` and the holding class should have `@Component` annotation

```java
@EventListener
public void handleContextRefreshEvent(ContextStartedEvent ctxStartEvt) {
    System.out.println("Context Start Event received.");
}
```
-- OR --
```java
@EventListener(classes = { ContextStartedEvent.class, ContextStoppedEvent.class })
public void handleMultipleEvents() {
    System.out.println("Multi-event listener invoked");
}
```
