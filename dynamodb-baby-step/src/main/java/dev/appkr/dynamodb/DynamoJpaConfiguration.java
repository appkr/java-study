package dev.appkr.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories
public class DynamoJpaConfiguration {

  @Bean
  public AWSCredentialsProvider awsCredentialsProvider() {
    final AWSCredentials awsCredentials = new BasicAWSCredentials("key", "secret");
    return new AWSStaticCredentialsProvider(awsCredentials);
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider credentialsProvider) {
    return AmazonDynamoDBClientBuilder
        .standard()
        .withCredentials(credentialsProvider)
        .withEndpointConfiguration(new EndpointConfiguration("http://localhost:8000", Regions.AP_NORTHEAST_2.getName()))
        .build();
  }

  public static class LocalDateTimeConverter implements DynamoDBTypeConverter<Date, LocalDateTime> {

    @Override
    public Date convert(LocalDateTime object) {
      if (object == null) {
        return null;
      }

      return Date.from(object.toInstant(ZoneOffset.UTC));
    }

    @Override
    public LocalDateTime unconvert(Date object) {
      if (object == null) {
        return null;
      }

      return object.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
  }
}
