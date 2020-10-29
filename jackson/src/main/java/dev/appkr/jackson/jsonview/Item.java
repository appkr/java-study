package dev.appkr.jackson.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

public class Item {

  @JsonView(Views.Public.class)
  public int id;

  @JsonView(Views.Public.class)
  public String itemName;

  @JsonView(Views.Internal.class)
  public String ownerName;

  public String otherName = "Other Name";

  public Item(int id, String itemName, String ownerName) {
    this.id = id;
    this.itemName = itemName;
    this.ownerName = ownerName;
  }
}