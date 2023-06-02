package dev.appkr.dynamodb;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Customer {

  String id;
  String name;
  String email;
  Instant regDate;

  public static TableSchema<Customer> getSchema() {
    return TableSchema.builder(Customer.class)
        .newItemSupplier(Customer::new)
        .addAttribute(String.class, a -> a
            .name("id")
            .getter(Customer::getId)
            .setter(Customer::setId)
            .tags(primaryPartitionKey()))
        .addAttribute(String.class, a -> a
            .name("email")
            .getter(Customer::getEmail)
            .setter(Customer::setEmail))
        .addAttribute(String.class, a -> a
            .name("name")
            .getter(Customer::getName)
            .setter(Customer::setName))
        .addAttribute(Instant.class, a -> a
            .name("regDate")
            .getter(Customer::getRegDate)
            .setter(Customer::setRegDate))
        .build();
  }
}
