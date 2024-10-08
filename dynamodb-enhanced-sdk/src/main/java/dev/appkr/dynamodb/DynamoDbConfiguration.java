package dev.appkr.dynamodb;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
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
  public DynamoDbAsyncClient dynamoDbAsyncClient() {
    final AwsBasicCredentials credentials = AwsBasicCredentials.create("key", "secret");
    final StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

    return DynamoDbAsyncClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.AP_NORTHEAST_2)
        .endpointOverride(URI.create("http://localhost:8000"))
        .build();
  }

  @Bean
  public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbClient) {
    return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(dynamoDbClient).build();
  }
}
