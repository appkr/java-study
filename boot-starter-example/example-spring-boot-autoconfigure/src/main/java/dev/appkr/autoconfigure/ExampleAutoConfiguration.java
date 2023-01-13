package dev.appkr.autoconfigure;

import dev.appkr.lib.ExampleBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  ExampleBean exampleBean() {
    return () -> "This is an example";
  }
}
