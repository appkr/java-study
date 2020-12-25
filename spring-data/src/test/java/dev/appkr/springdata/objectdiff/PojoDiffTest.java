package dev.appkr.springdata.objectdiff;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;
import dev.appkr.springdata.envers2.Address;
import dev.appkr.springdata.envers2.Person;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojoDiffTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Test
  public void testPojoDiff() {
    Address baseAddr = new Address("서울특별시 강남구 삼성동 159-17", 5);
    Person base = new Person("메쉬", "김", baseAddr);

    Address workingAddr = new Address("서울특별시 강남구 테헤란로 418", 13);
    Person working = new Person("부릉", "송", workingAddr);

    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    List<ChangeLog> changeLogs = new ArrayList<>();
    if (diff.hasChanges()) {

      // diff traversal
      // @see https://java-object-diff.readthedocs.io/en/latest/getting-started/#traversal
      diff.visit(new DiffNode.Visitor() {
        public void node(DiffNode node, Visit visit)
        {
          if (!node.hasChildren()) {
            final Object before = node.canonicalGet(base);
            final Object after = node.canonicalGet(working);

            changeLogs.add(ChangeLog.builder()
                .changedField(node.getPropertyName())
                .beforeValue(before)
                .afterValue(after)
                .memo(String.format("%s changed from %s to %s", node.getPropertyName(), before, after))
                .build()
            );
          }
        }
      });
    }

    log.info("changeLogs {}", changeLogs);
    /**
     * [
     *   ChangeLog(
     *     changedAt=2020-12-13T09:41:38.628161Z,
     *     changedBy=00000000-0000-0000-0000-000000000000,
     *     changedField=houseNumber,
     *     beforeValue=5,
     *     afterValue=13,
     *     memo=houseNumber changed from 5 to 13
     *   ),
     *   ChangeLog(
     *     changedAt=2020-12-13T09:41:38.628330Z,
     *     changedBy=00000000-0000-0000-0000-000000000000,
     *     changedField=streetName,
     *     beforeValue=서울특별시 강남구 삼성동 159-17,
     *     afterValue=서울특별시 강남구 테헤란로 418,
     *     memo=streetName changed from 서울특별시 강남구 삼성동 159-17 to 서울특별시 강남구 테헤란로 418
     *   ),
     *   ChangeLog(
     *     changedAt=2020-12-13T09:41:38.628419Z,
     *     changedBy=00000000-0000-0000-0000-000000000000,
     *     changedField=name,
     *     beforeValue=메쉬,
     *     afterValue=부릉,
     *     memo=name changed from 메쉬 to 부릉
     *   ),
     *   ChangeLog(
     *     changedAt=2020-12-13T09:41:38.628492Z,
     *     changedBy=00000000-0000-0000-0000-000000000000,
     *     changedField=surname,
     *     beforeValue=김,
     *     afterValue=송,
     *     memo=surname changed from 김 to 송
     *   )
     * ]
     */
  }
}
