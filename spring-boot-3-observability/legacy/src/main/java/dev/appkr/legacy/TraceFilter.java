package dev.appkr.legacy;

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
