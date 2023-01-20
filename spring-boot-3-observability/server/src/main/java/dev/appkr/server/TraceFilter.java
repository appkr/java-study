package dev.appkr.server;

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
