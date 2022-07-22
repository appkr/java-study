package dev.appkr.edge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;

@Configuration
@Slf4j(topic = "edge")
public class LogbookConfiguration {

  private final Tracer tracer;

  public LogbookConfiguration(Tracer tracer) {
    this.tracer = tracer;
  }

  @Bean
  public Logbook logbook() {
    return Logbook.builder()
        .correlationId(request -> {
          final Span currentSpan = tracer.currentSpan();
          if (currentSpan == null) {
            return new DefaultCorrelationId().generate(request);
          }

          return currentSpan.context().traceId();
        })
        .sink(new DefaultSink(new DefaultHttpLogFormatter(), new CustomHttpLogWriter()))
        .build();
  }

  class CustomHttpLogWriter implements HttpLogWriter {

    @Override
    public boolean isActive() {
      return true;
    }

    @Override
    public void write(final Precorrelation precorrelation, final String request) {
      log.info(request);
    }

    @Override
    public void write(final Correlation correlation, final String response) {
      log.info(response);
    }
  }
}
