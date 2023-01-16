package dev.appkr.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "example")
public class ExampleProperties extends dev.appkr.lib.ExampleProperties {

}
