package dev.appkr.dynamodb.model;

import dev.appkr.dynamodb.documentPoc.AddressConverter;
import dev.appkr.dynamodb.documentPoc.ContactConverter;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
public class User {

  @Getter(AccessLevel.NONE) String id;
  String name;
  @Getter(AccessLevel.NONE) List<Contact> contacts;
  @Getter(AccessLevel.NONE) Address address;
  Instant registeredAt;

  @DynamoDbPartitionKey
  public String getId() {
    return id;
  }

  @DynamoDbConvertedBy(AddressConverter.class)
  public Address getAddress() {
    return address;
  }

  @DynamoDbConvertedBy(ContactConverter.class)
  public List<Contact> getContacts() {
    return contacts;
  }
}
