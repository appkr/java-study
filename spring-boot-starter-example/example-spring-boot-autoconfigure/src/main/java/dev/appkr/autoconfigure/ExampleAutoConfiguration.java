package dev.appkr.autoconfigure;

import dev.appkr.lib.ExampleBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  ExampleBean exampleBean() {
    return () -> "good day";
  }
}
