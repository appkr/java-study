package springstudy.thread.baeldung;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ForkJoinPoolTest {

    @Test
    public void RecursiveTask를_처리한다() {
        TreeNode tree = new TreeNode(5,
            new TreeNode(3),
            new TreeNode(2,
                new TreeNode(2),
                new TreeNode(8)
            )
        );

        ForkJoinPool pool = ForkJoinPool.commonPool();
        int sum = pool.invoke(new CountingTask(tree));
        log.info("Task delegated");

        assertEquals(20, sum);

        // 16:17:44.719 [ForkJoinPool.commonPool-worker-1] INFO  springstudy.thread.baeldung.ForkJoinPoolTest - Handling task at ForkJoinPool.commonPool-worker-1
        // 16:17:44.723 [ForkJoinPool.commonPool-worker-1] INFO  springstudy.thread.baeldung.ForkJoinPoolTest - Handling task at ForkJoinPool.commonPool-worker-1
        // 16:17:44.723 [ForkJoinPool.commonPool-worker-1] INFO  springstudy.thread.baeldung.ForkJoinPoolTest - Handling task at ForkJoinPool.commonPool-worker-1
        // 16:17:44.723 [ForkJoinPool.commonPool-worker-1] INFO  springstudy.thread.baeldung.ForkJoinPoolTest - Handling task at ForkJoinPool.commonPool-worker-1
        // 16:17:44.724 [main] INFO  springstudy.thread.baeldung.ForkJoinPoolTest - Task delegated
    }

    static class TreeNode {

        int value;
        Set<TreeNode> children;

        TreeNode(int value, TreeNode... children) {
            this.value = value;
            this.children = Sets.newHashSet(children);
        }
    }

    static class CountingTask extends RecursiveTask<Integer> {

        private final TreeNode node;

        CountingTask(TreeNode node) {
            this.node = node;
        }

        @Override
        protected Integer compute() {
            return node.value + node.children.stream()
                .map(childNode -> {
                    log.info("Handling task at {}", Thread.currentThread().getName());
                    return new CountingTask(childNode).fork();
                })
                .mapToInt(ForkJoinTask::join)
                .sum();
        }
    }
}
