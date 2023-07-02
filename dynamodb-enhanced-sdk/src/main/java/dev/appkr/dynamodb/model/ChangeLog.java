package dev.appkr.dynamodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLog {

  String id;
  String type;
  String otherFields;

  public String getKey() {
    return id;
  }
}
