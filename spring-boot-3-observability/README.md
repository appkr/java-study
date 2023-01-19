## Spring Boot 3 Observability

From Spring Boot 3, a new observability feature was introduced, which substitutes `spring-cloud-starter-sleuth`.

- https://openvalue.blog/posts/2022/12/16/tracing-in-spring-boot-2-and-3/
- https://www.w3.org/TR/trace-context/

`traceId` and `spanId` are still available in log entries, but the format differs from the previous ones. In addition to that, the `X-B3-*` HTTP headers are no more used and replaced to `traceparent` and `tracestate`. 

```shell
# Spring Boot 2 (sleuth implementation)
X-B3-TraceId(tracd-id, traceId): a3ce929d0e0e4736 # 8byte in default; but opt in to 16byte by configuring properties
X-B3-SpanId(span-id, spanId): 00f067aa0ba902b7    # 8byte
```
```shell
# Spring Boot 3 (micrometer implementation)
traceparent: 00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01
# version = 00                                    # 1byte; version 00 format is (trace-id + "-" + parent-id + "-" + trace-flags) 
# trace-id = 4bf92f3577b34da6a3ce929d0e0e4736     # 16byte; All zeroes is invalid
# parent-id = 00f067aa0ba902b7                    # 8byte; All zeroes is invalid
# trace-flags = 01                                # 1byte; Currently, only one bit is used

tracestate: tracestate: rojo=00f067aa0ba902b7
# key = rojo
# value = 00f067aa0ba902b7 (which is same as the parent-id)
```

### Migration Guide From Sleuth(Spring Boot 2) To Micrometer(Spring Boot 3)

Add the following dependencies:
```groovy
// build.gradle
dependencies {
    implementation 'io.micrometer:micrometer-tracing'
    implementation 'io.micrometer:micrometer-tracing-bridge-brave'
}
```

Add the following log format:
```yaml
# application.yml
logging.pattern.level: "%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]"
```

### For Forward and Backward Compatability in Spring Boot 2

Set the following configuration properties:
```yaml
spring.sleuth.propagation.type: w3c,b3 # Enable both b3(X-B3-*), w3c(traceparent & tracestate) request headers
spring.sleuth.trace-id128: true        # Enable 128bit(16byte) traceId
```

Implement a Filter that emits trace-related HTTP response headers:
```java
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceFilter implements Filter {

  final Tracer tracer;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final Span currentSpan = tracer.currentSpan();
    if (currentSpan == null) {
      chain.doFilter(request, response);
      return;
    }

    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.addHeader("X-B3-TraceId", currentSpan.context().traceId());
    httpResponse.addHeader("X-B3-SpanId", currentSpan.context().spanId());
    httpResponse.addHeader("X-B3-ParentSpanId", currentSpan.context().parentId());
    httpResponse.addHeader("X-B3-Sampled", currentSpan.context().sampled() ? "1" : "0");
    httpResponse.addHeader("X-B3-Flags", "0");

    // For forward compatability
    if (currentSpan.context().parentId() != null) {
      // build a w3c format
      // @see https://www.w3.org/TR/trace-context/#parent-id
      // Vendors MUST ignore the traceparent when the parent-id is invalid (for example, if it contains non-lowercase hex characters).
      final String traceParent = String.format("00-%s-%s-%02d", formatTraceId(currentSpan.context().traceId()),
          currentSpan.context().parentId(), currentSpan.context().sampled() ? 1 : 0);
      final String traceState = String.format("sleuth-%s", currentSpan.context().parentId());

      httpResponse.addHeader("traceparent", traceParent);
      httpResponse.addHeader("tracestate", traceState);
    }

    chain.doFilter(request, response);
  }

  String formatTraceId(String in) {
    // @see https://www.w3.org/TR/trace-context/#interoperating-with-existing-systems-which-use-shorter-identifiers
    // When a system creates an outbound message and needs to generate a fully compliant 16 bytes trace-id from a shorter identifier,
    // it SHOULD left pad the original identifier with zeroes. e.g. 53ce929d0e0e4736 -> 000000000000000053ce929d0e0e4736
    return (in.length() == 16) ? "0000000000000000" + in : in;
  }
}
```

Test result:
```shell
legacy(Spring Boot 2.7.7) ---------------> server (Spring Boot 3.0.1)

# legacy log
[legacy,63c9ff8c3419a9133900b7dc7f79051c,3900b7dc7f79051c] REQUEST: {
  headers=[
    Accept:"text/plain, application/json, application/*+json, */*", 
    Content-Length:"0", 
    traceparent:"00-63c9ff8c3419a9133900b7dc7f79051c-3900b7dc7f79051c-01", 
    X-B3-TraceId:"63c9ff8c3419a9133900b7dc7f79051c", 
    X-B3-SpanId:"3900b7dc7f79051c", 
    X-B3-Sampled:"1"
  ]
}
[legacy,63c9ff8c3419a9133900b7dc7f79051c,3900b7dc7f79051c] RESPONSE: {
  headers=[
    X-B3-TraceId:"63c9ff8c3419a9133900b7dc7f79051c", 
    X-B3-SpanId:"1e77748230fcd43d", 
    X-B3-ParentSpanId:"3900b7dc7f79051c", 
    X-B3-Sampled:"1", 
    X-B3-Flags:"0", 
    traceparent:"00-63c9ff8c3419a9133900b7dc7f79051c-3900b7dc7f79051c-01", 
    tracestate:"micrometer-3900b7dc7f79051c", 
    Content-Type:"text/plain;charset=UTF-8", 
    Content-Length:"3", 
    Date:"Fri, 20 Jan 2023 02:42:20 GMT", 
    Keep-Alive:"timeout=60", 
    Connection:"keep-alive"
  ]
}
[legacy,,]                                                 Get a response <foo> from the server

# server log
[server,63c9ff8c3419a9133900b7dc7f79051c,1e77748230fcd43d] Got a request from the legacy
```

> **`Notable Guide`**
>
> Since the default propagation format is w3c you should set in your Sleuth & Boot 2.x application `spring.sleuth.propagation.type=w3c,b3` so that you publish the headers in 2 formats. One for the current Boot 2.x applications (b3) and one for the new Boot 3.x applications (w3c).
> 
> @see https://github.com/micrometer-metrics/tracing/wiki/Spring-Cloud-Sleuth-3.1-Migration-Guide

### For Forward and Backward Compatability in Spring Boot 3

Implement a Filter that emits trace-related HTTP response headers:
```java
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceFilter implements Filter {

  final Tracer tracer;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final Span currentSpan = tracer.currentSpan();
    if (currentSpan == null) {
      chain.doFilter(request, response);
      return;
    }

    // For backward compatibility
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.addHeader("X-B3-TraceId", currentSpan.context().traceId());
    httpResponse.addHeader("X-B3-SpanId", currentSpan.context().spanId());
    httpResponse.addHeader("X-B3-ParentSpanId", currentSpan.context().parentId());
    httpResponse.addHeader("X-B3-Sampled", currentSpan.context().sampled() ? "1" : "0");
    httpResponse.addHeader("X-B3-Flags", "0");

    if (currentSpan.context().parentId() != null) {
      // build a w3c format
      // @see https://www.w3.org/TR/trace-context/#parent-id
      // Vendors MUST ignore the traceparent when the parent-id is invalid (for example, if it contains non-lowercase hex characters).
      final String traceParent = String.format("00-%s-%s-%02d", currentSpan.context().traceId(),
          currentSpan.context().parentId(), currentSpan.context().sampled() ? 1 : 0);
      final String traceState = String.format("micrometer-%s", currentSpan.context().parentId());

      httpResponse.addHeader("traceparent", traceParent);
      httpResponse.addHeader("tracestate", traceState);
    }

    chain.doFilter(request, response);
  }
}
```

Test result:
```shell
client(Spring Boot 3.0.1) ---------------> legacy (Spring Boot 2.7.7)

# client log
[client,,]                                                 REQUEST: {
  headers=[
    traceparent:"00-63ca026e4838b4340b6fd4bf3b4ac59d-0b6fd4bf3b4ac59d-00", 
    Accept:"text/plain, application/json, application/*+json, */*", 
    Content-Length:"0"
  ]
}
[client,,]                                                 RESPONSE: {
  headers=[
    X-B3-TraceId:"63ca026e4838b4340b6fd4bf3b4ac59d", 
    X-B3-SpanId:"8579c8403b310cba", 
    X-B3-ParentSpanId:"0b6fd4bf3b4ac59d", 
    X-B3-Sampled:"0", 
    X-B3-Flags:"0", 
    traceparent:"00-63ca026e4838b4340b6fd4bf3b4ac59d-0b6fd4bf3b4ac59d-00", 
    tracestate:"sleuth-0b6fd4bf3b4ac59d", 
    Content-Type:"text/plain;charset=UTF-8", 
    Content-Length:"3", 
    Date:"Fri, 20 Jan 2023 02:54:38 GMT", 
    Keep-Alive:"timeout=60", 
    Connection:"keep-alive"
  ]
}
[client,,]                                                 Got a response <foo> from the legacy

# legacy log
[legacy,63ca026e4838b4340b6fd4bf3b4ac59d,8579c8403b310cba] Got a request
```

---

### Full Stack for Observability

Run:
```shell
$ git checkout 6f570b3

# Run the backend components
$ ./gradlew composeUp

# Start server(Spring Boot 3) and client(Spring Boot 3)
$ ./gradlew :server:bootRun
$ ./gradlew :client:bootRun

# Observe the result in Grafana and Prometheus
$ open http://localhost:3000
$ open http://localhost:9090
```

Backend components are:

Name|Port|Role
---|---|---
Tempo|http 3200, jaeger 14268, zipkin 9411|distributed tracing backend; @see https://grafana.com/oss/tempo/
Loki|3100|log aggregation system; @see https://grafana.com/oss/loki/
Prometheus|http 9090|monitoring and alerting toolkit; @see https://prometheus.io/docs/introduction/overview/
Grafana|http 3000|visualization tool

- https://spring.io/blog/2022/10/12/observability-with-spring-boot-3
