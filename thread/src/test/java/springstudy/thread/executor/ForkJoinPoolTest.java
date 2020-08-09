package springstudy.thread.executor;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ForkJoinPoolTest {

    @Test
    public void RecursiveTask를_처리한다() {
        TreeNode tree = new TreeNode("r", 5,
            new TreeNode("a", 3),
            new TreeNode("b", 2,
                new TreeNode("b-1", 2),
                new TreeNode("b-2", 8,
                    new TreeNode("b-2-1", 100),
                    new TreeNode("b-2-2", 50)
                )
            ),
            new TreeNode("c", 30),
            new TreeNode("d", 1,
                new TreeNode("d-1", 1,
                    new TreeNode("d-1-a", 1,
                        new TreeNode("d-1-b", 1,
                            new TreeNode("d-1-c", 1,
                                new TreeNode("d-1-d", 1,
                                    new TreeNode("d-1-e", 1,
                                        new TreeNode("d-1-f", 1,
                                            new TreeNode("d-1-g", 1,
                                                new TreeNode("d-1-h", 1)
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );

        // ForkJoinPool pool = new ForkJoinPool(10);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int sum = pool.invoke(new CountingTask(tree));
        log.info("Task delegated to {}", pool);

        assertEquals(210, sum);

        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=a, value=3) into CountingTask
        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=b, value=2) into CountingTask
        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=b-1, value=2) into CountingTask
        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=b-2, value=8) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=b-2-1, value=100) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=b-2-2, value=50) into CountingTask
        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=c, value=30) into CountingTask
        // [Test worker] INFO                       Wrapping ForkJoinPoolTest.TreeNode(name=d, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-a, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-b, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-c, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-d, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-e, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-3] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-f, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-5] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-g, value=1) into CountingTask
        // [ForkJoinPool.commonPool-worker-5] INFO  Wrapping ForkJoinPoolTest.TreeNode(name=d-1-h, value=1) into CountingTask
        // [Test worker] INFO                       Task delegated to java.util.concurrent.ForkJoinPool@57695d1f[Running, parallelism = 3, size = 3, active = 0, running = 0, steals = 3, tasks = 0, submissions = 0]
    }

    @ToString(of = {"name", "value"})
    static class TreeNode {
        @Getter String name;
        @Getter int value;
        Set<TreeNode> children;

        TreeNode(String name, int value, TreeNode... children) {
            this.name = name;
            this.value = value;
            this.children = Sets.newHashSet(children);
        }
    }

    static class CountingTask extends RecursiveTask<Integer> {
        @Getter
        private final TreeNode node;

        CountingTask(TreeNode node) {
            this.node = node;
        }

        @Override
        protected Integer compute() {
            return node.value + node.children.stream()
                .map(childNode -> {
                    log.info("Wrapping {} into CountingTask", childNode);
                    return new CountingTask(childNode).fork();
                })
                .mapToInt(ForkJoinTask::join)
                .sum();
        }
    }
}
