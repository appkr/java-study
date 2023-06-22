package dev.appkr.dynamodb.documentPoc;

import dev.appkr.dynamodb.model.Contact;
import dev.appkr.dynamodb.model.Contact.Type;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ContactConverter implements AttributeConverter<Contact> {

  @Override
  public AttributeValue transformFrom(Contact input) {
    final Map<String, AttributeValue> map = Map.of("type", AttributeValue.fromS(input.getType().toString()),
        "value", AttributeValue.fromS(input.getValue()));
    return AttributeValue.fromM(map);
  }

  @Override
  public Contact transformTo(AttributeValue input) {
    return new Contact(
        Type.valueOf(input.m().get("type").s()),
        input.m().get("value").s()
    );
  }

  @Override
  public EnhancedType<Contact> type() {
    return EnhancedType.of(Contact.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.M;
  }
}
