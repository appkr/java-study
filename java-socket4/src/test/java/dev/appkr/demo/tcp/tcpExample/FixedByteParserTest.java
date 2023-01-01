package dev.appkr.demo.tcp.tcpExample;

import dev.appkr.demo.tcp.Item;
import dev.appkr.demo.tcp.Packet;
import dev.appkr.demo.tcp.TcpMessage;
import dev.appkr.demo.tcp.TcpMessageTemplateFactory;
import dev.appkr.demo.tcp.config.TcpClientProperties;
import dev.appkr.demo.tcp.visitor.Parser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class FixedByteParserTest {

  final TcpClientProperties properties = new TcpClientProperties();

  @Test
  void parse() {
    // given
    final String tcpMessage = "020000022020030417050900001053202003041705093049900000000000078006272007000008000124900200000150000000150000000000000000000000080001089002000002500000002500000000000000000000000088100830010000000200000000200000000000000000880111778440900100000150000000150000000000000000004001686301555003000001500000003000000000000000000088010137700870030000029000000058000000000000000000000008000126300200000150000000150000000000000000000000000000000000000000000000000■ 고객주소: 대치4동 890-12 12층                 ■ 고객요청사항:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       050218031961   1800-8255      ";

    // when
    final Packet packet = new Packet("root", tcpMessage.getBytes(properties.getCharset()));
    final TcpMessageTemplateFactory templateFactory = new FixedByteTcpMessageTemplateFactory();
    final Parser parser = new FixedByteParser(templateFactory);
    packet.accept(parser);

    // then
    log.info("{}", packet.toMap());
    /**
     * {
     *     note=Item(name=note, pointer=600, value=■ 고객주소: 대치4동 890-12 12층                 ■ 고객요청사항:),
     *     orderNo=Item(name=orderNo, pointer=20, value=00000000000078006272),
     *     serviceCode=Item(name=serviceCode, pointer=2, value=00),
     *     pluCount=Item(name=pluCount, pointer=3, value=007),
     *     customerTelephone=Item(name=customerTelephone, pointer=15, value=050218031961),
     *     couponNo=Item(name=couponNo, pointer=20, value=00000000000000000000),
     *     transactionHms=Item(name=transactionHms, pointer=6, value=170509),
     *     bodyLength=Item(name=bodyLength, pointer=4, value=1053),
     *     dlvHms=Item(name=dlvHms, pointer=6, value=170509),
     *     transactionYmd=Item(name=transactionYmd, pointer=8, value=20200304),
     *     responseCode=Item(name=responseCode, pointer=4, value=0000),
     *     dlvYmd=Item(name=dlvYmd, pointer=8, value=20200304),
     *     0=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=0000080001249),
     *             Item(name=quantity, pointer=3, value=002),
     *             Item(name=itemUrpc, pointer=9, value=000001500),
     *             Item(name=receiptAmount, pointer=9, value=000001500),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     1=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=0000080001089),
     *             Item(name=quantity, pointer=3, value=002),
     *             Item(name=itemUrpc, pointer=9, value=000002500),
     *             Item(name=receiptAmount, pointer=9, value=000002500),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     2=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=0000008810083),
     *             Item(name=quantity, pointer=3, value=001),
     *             Item(name=itemUrpc, pointer=9, value=000000020),
     *             Item(name=receiptAmount, pointer=9, value=000000020),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     3=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=8801117784409),
     *             Item(name=quantity, pointer=3, value=001),
     *             Item(name=itemUrpc, pointer=9, value=000001500),
     *             Item(name=receiptAmount, pointer=9, value=000001500),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     4=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=4001686301555),
     *             Item(name=quantity, pointer=3, value=003),
     *             Item(name=itemUrpc, pointer=9, value=000001500),
     *             Item(name=receiptAmount, pointer=9, value=000003000),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     5=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=8801013770087),
     *             Item(name=quantity, pointer=3, value=003),
     *             Item(name=itemUrpc, pointer=9, value=000002900),
     *             Item(name=receiptAmount, pointer=9, value=000005800),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     6=Packet(
     *         messageComponents=[
     *             Item(name=pluCode, pointer=13, value=0000080001263),
     *             Item(name=quantity, pointer=3, value=002),
     *             Item(name=itemUrpc, pointer=9, value=000001500),
     *             Item(name=receiptAmount, pointer=9, value=000001500),
     *             Item(name=eventCode, pointer=13, value=0000000000000),
     *             Item(name=eventApplicationAmount, pointer=3, value=000)
     *         ]
     *     ),
     *     messageType=Item(name=messageType, pointer=4, value=0200),
     *     delegatedCsContact=Item(name=delegatedCsContact, pointer=15, value=1800-8255),
     *     comCode=Item(name=comCode, pointer=2, value=02),
     *     csContact=Item(name=csContact, pointer=11, value=00000000000),
     *     storeCode=Item(name=storeCode, pointer=5, value=30499)
     * }
     */

    final int noOfSubPacket = ((FixedByteTcpMessageTemplateFactory) templateFactory)
        .getNoOfSubPacket(tcpMessage.getBytes(properties.getCharset()));
    IntStream.range(0, noOfSubPacket)
        .mapToObj(String::valueOf)
        .forEach(subPacketIndex -> {
          final Packet subPacket = (Packet) (packet.toMap().get(subPacketIndex));
          log.info("{}", subPacket.toMap());
          /**
           * {
           *     pluCode=Item(name=pluCode, pointer=13, value=0000080001249),
           *     eventCode=Item(name=eventCode, pointer=13, value=0000000000000),
           *     receiptAmount=Item(name=receiptAmount, pointer=9, value=000001500),
           *     quantity=Item(name=quantity, pointer=3, value=002),
           *     itemUrpc=Item(name=itemUrpc, pointer=9, value=000001500),
           *     eventApplicationAmount=Item(name=eventApplicationAmount, pointer=3, value=000)
           * }
           */
        });
  }

  static class FixedByteTcpMessageTemplateFactory implements TcpMessageTemplateFactory {

    @Override
    public List<TcpMessage> create(byte[] tcpMessage) {
      final List<TcpMessage> components = new ArrayList<>();
      components.add(Item.of("messageType", 4));
      components.add(Item.of("serviceCode", 2));
      components.add(Item.of("comCode", 2));
      components.add(Item.of("transactionYmd", 8));
      components.add(Item.of("transactionHms", 6));
      components.add(Item.of("responseCode", 4));
      components.add(Item.of("bodyLength", 4));
      components.add(Item.of("dlvYmd", 8));
      components.add(Item.of("dlvHms", 6));
      components.add(Item.of("storeCode", 5));
      components.add(Item.of("orderNo", 20));
      components.add(Item.of("pluCount", 3));

      final int noOfSubPacket = getNoOfSubPacket(tcpMessage);
      for (int i = 0; i < noOfSubPacket; i++) {
        Packet subPacket = new Packet("sub");
        subPacket.add(Item.of("pluCode", 13));
        subPacket.add(Item.of("quantity", 3));
        subPacket.add(Item.of("itemUrpc", 9));
        subPacket.add(Item.of("receiptAmount", 9));
        subPacket.add(Item.of("eventCode", 13));
        subPacket.add(Item.of("eventApplicationAmount", 3));
        components.add(subPacket);
      }

      components.add(Item.of("couponNo", 20));
      components.add(Item.of("csContact", 11));
      components.add(Item.of("note", 600));
      components.add(Item.of("customerTelephone", 15));
      components.add(Item.of("delegatedCsContact", 15));

      return components;
    }

    public int getNoOfSubPacket(byte[] tcpMessage) {
      // 고정문자열 72 bytes + 가변문자열 n bytes + 고정문자열 661 bytes = n + 733
      // 가변문자열 n은 50의 배수
      // 즉, 서브 패킷의 개수 = (전체 길이 - 733) / 50
      return (tcpMessage.length - 733) / 50;
    }
  }
}
