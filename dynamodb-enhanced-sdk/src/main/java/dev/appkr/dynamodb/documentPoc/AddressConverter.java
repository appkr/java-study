package dev.appkr.dynamodb.documentPoc;

import dev.appkr.dynamodb.model.Address;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AddressConverter implements AttributeConverter<Address> {

  @Override
  public AttributeValue transformFrom(Address input) {
    final Map<String, AttributeValue> map = Map.of("baseAddress", AttributeValue.fromS(input.getBaseAddress()),
        "detailAddress", AttributeValue.fromS(input.getDetailAddress()));
    return AttributeValue.fromM(map);
  }

  @Override
  public Address transformTo(AttributeValue input) {
    return new Address(
        input.m().get("baseAddress").s(),
        input.m().get("detailAddress").s()
    );
  }

  @Override
  public EnhancedType<Address> type() {
    return EnhancedType.of(Address.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.M;
  }
}
