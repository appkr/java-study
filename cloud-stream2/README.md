## Spring Cloud Stream

- https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kinesis-samples/kinesis-produce-consume

```bash
$ ./gradlew clean build
```

#### Run the application
Run message producer
```bash
$ java -jar build/libs/cloud-stream2-0.0.1-SNAPSHOT.jar --server.port=64398 --originator=MessageProducer
```

Run message consumer
```bash
$ java -jar build/libs/cloud-stream2-0.0.1-SNAPSHOT.jar --server.port=64399 --originator=MessageConsumer
```

#### Test the application
Create ORDER on the producer
```bash
$ curl -s -X POST http://localhost:64398/orders -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"name":"pencil"}' | jq
# {
#   "id": "bec57cd2-72a4-40f8-942e-9c73820de92f",
#   "name": "pencil"
# }
```

The producer log. Saving the order on response to the api call, but DO NOT HANDLE MESSAGE since the message was created by himself.
```bash
2019-06-03 18:07:49.602  INFO 80741 --- [io-64398-exec-3] s.cloudstream2.stream.OrdersSource       : Event sent: Event{id=null, subject=Order{id=bec57cd2-72a4-40f8-942e-9c73820de92f, name='pencil'}, type='ORDER', originator='MessageProducer'}
2019-06-03 18:07:49.603  INFO 80741 --- [ryIMI_Nnyaocg-1] uration$$EnhancerBySpringCGLIB$$e4c67da6 : An order has been created on this server: Event{id=null, subject=Order{id=bec57cd2-72a4-40f8-942e-9c73820de92f, name='pencil'}, type='ORDER', originator='MessageProducer'}
```

The consumer log. HANDLE MESSAGE, but DO NOT SAVE since the id exists in the database.
```bash
2019-06-03 18:07:49.603  INFO 80782 --- [Z-H8Czm-LwExA-1] uration$$EnhancerBySpringCGLIB$$7ff0d388 : An order has been received: Event{id=null, subject=Order{id=bec57cd2-72a4-40f8-942e-9c73820de92f, name='pencil'}, type='ORDER', originator='MessageProducer'}
```

List the order against any server.
```bash
$ curl -s -X GET http://localhost:64398/orders | jq
# [
#   {
#     "id": "bec57cd2-72a4-40f8-942e-9c73820de92f",
#     "name": "pencil"
#   }
# ]
```

#### Sequence

> Api call from Api client
1. Create `Order` entity
2. `OrderController` new up a `Event`
3. `OrderController` calls `OrderSource.sendOrder(Event)`
4. `OrderSource` calls `OrderProcessor.orderOut(): MessageChannel`
5. `OrderSource` calls `MessageChannel.send(GenericMessage)` 

> On receiving message
1. `OrderStreamConfiguration.processOrder()` being invoked
2. Handle message
    1. If the message was fired by the other process, tries to save the Order entity
    2. If the message was fired by himself, do nothing 
