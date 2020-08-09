package springstudy.thread.mutex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@Slf4j
public class MutexTest {

  @Test
  public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), count);
    log.info("expected count: {}", count);
    log.info("actual count: {}", uniqueSequences.size());
  }

  @Test
  public void testSequenceGeneratorWithSynchronizedMethod() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod(), count);
    assertEquals(count, uniqueSequences.size());
  }

  @Test
  public void testSequenceGeneratorWithSynchronizedBlock() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock(), count);
    assertEquals(count, uniqueSequences.size());
  }

  @Test
  public void testSequenceGeneratorWithReentrantLock() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingReentrantLock(), count);
    assertEquals(count, uniqueSequences.size());
  }

  @Test
  public void testSequenceGeneratorWithSemaphore() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSemaphore(), count);
    assertEquals(count, uniqueSequences.size());
  }

  @Test
  public void testSequenceGeneratorWithGuavaMonitor() throws Exception {
    int count = 1000;
    final Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingGuavaMonitor(), count);
    assertEquals(count, uniqueSequences.size());
  }

  private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
    final ExecutorService es = Executors.newFixedThreadPool(3);
    Set<Integer> uniqueSequences = new LinkedHashSet<>();
    List<Future<Integer>> futures = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      futures.add(es.submit(generator::getNextSequence));
    }

    for (Future<Integer> future: futures) {
      uniqueSequences.add(future.get());
    }

    es.awaitTermination(1, TimeUnit.SECONDS);
    es.shutdown();

    return uniqueSequences;
  }
}
