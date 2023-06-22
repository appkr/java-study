package dev.appkr.dynamodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

  Type type;
  String value;

  public enum Type {
    PHONE,
    EMAIL
  }
}
