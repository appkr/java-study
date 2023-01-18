## Spring Boot 3 Observability

From Spring Boot 3, a new observability feature was introduced, which substitutes `spring-cloud-starter-sleuth`.

- https://spring.io/blog/2022/10/12/observability-with-spring-boot-3
- https://www.w3.org/TR/trace-context/

`traceId` and `spanId` are still available in log entries, but the format differs from the previous ones. In addition to that, the `X-B3-TraceId`, `X-B3-SpanId` HTTP headers are no more used and replaced to `traceparent` and `tracestate`. 

```shell
traceparent: 00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01
# base16(version) = 00
# base16(trace-id) = 4bf92f3577b34da6a3ce929d0e0e4736
# base16(parent-id) = 00f067aa0ba902b7
# base16(trace-flags) = 01  // sampled

tracestate: tracestate: rojo=00f067aa0ba902b7
# key = rojo
# value = 00f067aa0ba902b7 (which is same as the parent-id)
```

### Backend Components

Name|Port|Role
---|---|---
Tempo|http 3200, jaeger 14268, zipkin 9411|distributed tracing backend; @see https://grafana.com/oss/tempo/
Loki|3100|log aggregation system; @see https://grafana.com/oss/loki/
Prometheus|http 9090|monitoring and alerting toolkit; @see https://prometheus.io/docs/introduction/overview/
Grafana|http 3000|visualization tool

Run the components
```shell
$ ./gradlew composeUp
```
