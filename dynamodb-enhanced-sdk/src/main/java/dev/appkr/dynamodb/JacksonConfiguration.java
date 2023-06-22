package dev.appkr.dynamodb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

  @Bean
  public JavaTimeModule javaTimeModule() {
    return new JavaTimeModule();
  }

  @Bean
  public ObjectMapper jacksonObjectMapper(JavaTimeModule javaTimeModule) {
    final ObjectMapper mapper = new ObjectMapper();

    mapper.registerModules(javaTimeModule);

    mapper.disable(
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    mapper.disable(
        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
        SerializationFeature.FAIL_ON_EMPTY_BEANS);

    return mapper;
  }
}
