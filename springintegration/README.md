Essence of Enterprise Integration Model:
> "Pipe & Filter"

## Spring Integration
- `Message`: the unit of data transfer within a Spring Integration context.
- `MessageChannel`: the pipe by which messages are relayed from one system to another.
- `AbstractEndpoint`
    - `Service Activator`: is a generic endpoint for connecting a service(POJO) instance to the messaging system.
    - `Adapter`: literally an adapter as we know it from plugging into a wall socket or electronic device.
        - Inbound adapters: `@InboundChannelAdapter` bring in messages from the external system
        - Outbound adapters: are used to send messages outwards
    
|MVC|EAI[^1]|
|---|---|
|uri|channel|
|controller|endpoint|

### Service Activator
```java
@ServiceActivator
public String myService(String payload) {}
```
1. The framework extracts a `String` payload
2. Invokes the method, `myService()`
3. Wraps the result in a message
4. send to the next component in the flow (The original headers are copied to the new message)


[^1]: EAI: Enterprise Application Integration

## References
- https://www.enterpriseintegrationpatterns.com/patterns/messaging/
- https://www.baeldung.com/spring-integration
- https://www.youtube.com/watch?v=icIosLjHu3I&list=PLr2Nvl0YJxI5-QasO8XY5m8Fy34kG-YF2
- https://www.youtube.com/watch?v=lla5jtKIn10&list=PLO0KWyajXMh6HbVTnf7YqwbEeZU6kuKJa 
    -> For documentation @see `springstudy.springintegration.controller.IntegrationController`