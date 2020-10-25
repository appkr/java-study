package dev.appkr.jackson.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

public class User {

  public int id;

  @JsonView(Views.Public.class)
  public String name;

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public User() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
