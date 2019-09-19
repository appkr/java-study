## Spring Cloud Stream

> `Source` can be used for an application which has a __single outbound channel__. Think of it as a Producer, therefore it has a return.
>
> `Sink` can be used for an application which has a __single inbound channel__. Think of it as a Consumer, which returns void.
>
> `Processor` can be used for an application which has __both an inbound channel and an outbound channel__.
>
> @see [https://docs.spring.io/spring-cloud-stream/docs/Brooklyn.RELEASE/reference/html/_programming_model.html#__literal_source_literal_literal_sink_literal_and_literal_processor_literal](https://docs.spring.io/spring-cloud-stream/docs/Brooklyn.RELEASE/reference/html/_programming_model.html#__literal_source_literal_literal_sink_literal_and_literal_processor_literal)

- https://docs.spring.io/spring-cloud-stream-app-starters/docs/current/reference/htmlsingle/
- https://www.baeldung.com/spring-cloud-stream
- https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/2.1.1.RELEASE/single/spring-cloud-stream.html#_quick_start
- https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kinesis-samples/kinesis-produce-consume
- https://github.com/JacobASeverson/stream-example-kinesis

#### Up and runnign RabbitMQ
- https://hub.docker.com/_/rabbitmq/
```bash
docker run \
    --name rabbit \
    -v $HOME/rabbit_data:/var/lib/rabbitmq \
    --network mynet \
    -p 5672:5672 \
    -p 8000:15672 \
    rabbitmq:3-management
```

```bash
$ open http://localhost:8000
# guest / guest

```