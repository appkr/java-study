package dev.appkr.dynamodb;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sts.StsClient;

@Configuration
public class DynamoDbConfiguration {

  @Bean
  public StsClient stsClient() {
    return StsClient.builder()
        .region(Region.AP_NORTHEAST_2)
        .credentialsProvider(InstanceProfileCredentialsProvider.create())
        .build();
  }

  @Bean
  public DynamoDbClient dynamoDbClient() {
    final AwsBasicCredentials credentials = AwsBasicCredentials.create("key", "secret");
    final StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

    return DynamoDbClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.AP_NORTHEAST_2)
        .endpointOverride(URI.create("http://localhost:8000"))
        .build();
  }
}
