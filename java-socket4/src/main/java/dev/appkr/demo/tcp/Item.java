package dev.appkr.demo.tcp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Item implements TcpMessage {

  @Setter
  private String name;
  /**
   * 문자열의 길이에 따라 조합된 전문 메시지일 때는 length를 의미함
   * 구분자에 의해 조합된 전문 메시지일 때는 index를 의미함
   */
  private int pointer;
  @Setter
  private String value;

  public static Item of(String name, int pointer, String value) {
    return new Item(name, pointer, value);
  }

  public static Item of(String name, int pointer) {
    return new Item(name, pointer, "");
  }

  private Item(String name, int pointer, String value) {
    this.name = name;
    this.pointer = pointer;
    this.value = value;
  }
}
