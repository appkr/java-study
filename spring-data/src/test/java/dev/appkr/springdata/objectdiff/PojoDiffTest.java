package dev.appkr.springdata.objectdiff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.*;
import de.danielbechler.diff.node.DiffNode.State;
import de.danielbechler.diff.node.DiffNode.Visitor;
import de.danielbechler.diff.path.NodePath;
import dev.appkr.springdata.envers2.Address;
import dev.appkr.springdata.envers2.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Test
  public void testMapDiff() {
    Map<String, String> working = Collections.singletonMap("item", "foo");
    Map<String, String> base = Collections.singletonMap("item", "bar");
    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    assertTrue(diff.hasChanges());
    assertEquals(1, diff.childCount());

    NodePath itemPath = NodePath.startBuilding().mapKey("item").build();
    assertEquals(DiffNode.State.CHANGED, diff.getChild(itemPath).getState());

    log.info("diff {}", diff);
    log.info("itemPath {}", itemPath);
  }

  @Test
  public void testTraverseNode() {
    Map<String, String> working = Collections.singletonMap("item", "foo");
    Map<String, String> base = Collections.singletonMap("item", "bar");
    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    diff.visit(new Visitor() {
      @Override
      public void node(DiffNode node, Visit visit) {
        log.info("traverse changes {} => {}", node.getPath(), node.getState());
      }
    });
  }

  @Test
  public void testReadingValues() {
    Map<String, String> working = Collections.singletonMap("item", "foo");
    Map<String, String> base = Collections.singletonMap("item", "bar");
    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    diff.visit(new Visitor() {
      @Override
      public void node(DiffNode node, Visit visit) {
        final Object baseValue = node.canonicalGet(base);
        final Object workingValue = node.canonicalGet(working);
        log.info("{} changed from {} to {}", node.getPath(), baseValue, workingValue);
      }
    });
  }

  @Test
  public void testPatching() {
    Map<String, String> working = Collections.singletonMap("item", "foo");
    Map<String, String> base = Collections.singletonMap("item", "bar");
    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    final Map<String, String> head = new HashMap<String, String>(base);
    head.put("another", "map");

    diff.visit(new Visitor() {
      @Override
      public void node(DiffNode node, Visit visit) {
        // only leaf-nodes with changes
        if (node.hasChanges() && !node.hasChildren()) {
          node.canonicalSet(head, node.canonicalGet(working));
        }
      }
    });

    log.info("new map {}", head);
  }

  @Test
  public void testBuiltinVisitors() {
    Map<String, String> working = Collections.singletonMap("item", "foo");
    Map<String, String> base = Collections.singletonMap("item", "bar");

    DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

    diff.visit(new CategoryFilteringVisitor());
    diff.visit(new NodeHierarchyVisitor());
    diff.visit(new NodePathVisitor(NodePath.startBuilding().mapKey("item").build()));
    diff.visit(new StateFilteringVisitor(State.CHANGED));
    diff.visit(new ToMapPrintingVisitor(working, base));
    diff.visit(new PrintingVisitor(working, base));
  }
}
