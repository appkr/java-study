package dev.appkr.springdata.objectdiff;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import java.util.ArrayList;
import java.util.List;

public class ChangeLogCollector {

  private Copyable base;
  private Copyable working;

  public ChangeLogCollector(Copyable working) {
    try {
      this.base = working.copy();
    } catch (CloneNotSupportedException e) {
    }
    this.working = working;
  }

  public List<ChangeLog> collect() {
    final List<ChangeLog> changeLogs = new ArrayList<>();

    final DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);
    if (diff.hasChanges()) {
      diff.visit((node, visit) -> {
        if (!node.hasChildren()) {
          final String changedField = getChangedFieldName(node);
          final Object before = node.canonicalGet(base);
          final Object after = node.canonicalGet(working);
          changeLogs.add(ChangeLog.builder()
              .changedField(changedField)
              .beforeValue(before)
              .afterValue(after)
              .memo(String.format("%s changed from %s to %s", changedField, before, after))
              .build());
        }
      });
    }

    return changeLogs;
  }

  private String getChangedFieldName(DiffNode node) {
    final String[] changedFieldName = {node.getPropertyName()};
    node.visitParents((pNode, visit) -> {
      if (!pNode.isRootNode()) {
        changedFieldName[0] = pNode.getPropertyName() + "." + changedFieldName[0];
      }
    });

    return changedFieldName[0];
  }
}
