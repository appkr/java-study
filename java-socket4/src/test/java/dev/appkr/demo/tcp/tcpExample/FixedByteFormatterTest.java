package dev.appkr.demo.tcp.tcpExample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.appkr.demo.tcp.Item;
import dev.appkr.demo.tcp.Packet;
import dev.appkr.demo.tcp.config.TcpClientProperties;
import dev.appkr.demo.tcp.visitor.Formatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class FixedByteFormatterTest {

  final TcpClientProperties properties = new TcpClientProperties();

  @Test
  void format() {
    // given
    final Packet packet = createPacket();
    final Formatter formatter = new FixedByteFormatter();

    // when
    packet.accept(formatter);

    // then
    assertEquals(getLength(packet), packet.getTcpMessage().length); // 783
    log.info("{}", new String(packet.getTcpMessage(), properties.getCharset()));
    /**
     * 00200002202003041705090000078320200304170509304990000000000007800627200700000000012490020000015000000015000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000■ 고객주소: 대치4동 890-12 12층                 ■ 고객요청사항:)0000502180319610000001800-8255
     */
  }

  private int getLength(Packet packet) {
    return packet.getMessageComponents().stream()
        .mapToInt(component -> {
          if (component instanceof Item) {
            return component.getPointer();
          }

          return getLength((Packet) component);
        })
        .reduce(Integer::sum)
        .orElse(0);
  }

  private Packet createPacket() {
    final Packet rootPacket = new Packet("root");
    rootPacket.add(Item.of("messageType", 4, "20"));
    rootPacket.add(Item.of("serviceCode", 2, ""));
    rootPacket.add(Item.of("comCode", 2, "2"));
    rootPacket.add(Item.of("transactionYmd", 8, "20200304"));
    rootPacket.add(Item.of("transactionHms", 6, "170509"));
    rootPacket.add(Item.of("responseCode", 4, ""));
    rootPacket.add(Item.of("bodyLength", 4, "783"));
    rootPacket.add(Item.of("dlvYmd", 8, "20200304"));
    rootPacket.add(Item.of("dlvHms", 6, "170509"));
    rootPacket.add(Item.of("storeCode", 5, "30499"));
    rootPacket.add(Item.of("orderNo", 20, "78006272"));
    rootPacket.add(Item.of("pluCount", 3, "7"));

    Packet subPacket = new Packet("sub");
    subPacket.add(Item.of("pluCode", 13, "1249"));
    subPacket.add(Item.of("quantity", 3, "2"));
    subPacket.add(Item.of("itemUrpc", 9, "1500"));
    subPacket.add(Item.of("receiptAmount", 9, "1500"));
    subPacket.add(Item.of("eventCode", 13, ""));
    subPacket.add(Item.of("eventApplicationAmount", 3, ""));
    rootPacket.add(subPacket);

    rootPacket.add(Item.of("couponNo", 20, ""));
    rootPacket.add(Item.of("csContact", 11, ""));
    rootPacket.add(Item.of("note", 600, "■ 고객주소: 대치4동 890-12 12층                 ■ 고객요청사항:)"));
    rootPacket.add(Item.of("customerTelephone", 15, "050218031961"));
    rootPacket.add(Item.of("delegatedCsContact", 15, "1800-8255"));

    return rootPacket;
  }
}
