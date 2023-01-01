package dev.appkr.demo.tcp.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.appkr.demo.tcp.config.TcpClientProperties;
import dev.appkr.demo.tcp.config.TcpClientProperties.Pool;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

@Slf4j
@Disabled("For this test to be working correctly, the EcoServer must be working...")
class TcpClientTest {

  final TcpClientProperties properties = new TcpClientProperties();
  final Pool poolConfig = properties.getPool();

  @Test
  void disposableTcpClient() throws Exception {
    final TcpClient client = new DisposableTcpClient(properties.getHost(), properties.getPort(),
        properties.getCharset());

    final String message = "안녕하세요?\n";
    client.write(message.getBytes(properties.getCharset()));
    final byte[] response = client.read();

    assertEquals("안녕하세요?", new String(response, properties.getCharset()));
  }

  @Test
  void pooledTcpClient() throws Exception {
    final TcpClient client = new PooledTcpClient(properties.getHost(), properties.getPort(),
        properties.getCharset(), poolConfig.getMinIdle(), poolConfig.getMaxIdle(),
        poolConfig.getMaxTotal());

    final ObjectPool<Tuple> pool = getPool(client);
    assertEquals(poolConfig.getMinIdle(), pool.getNumIdle());

    final String message = "안녕하세요?\n";
    client.write(message.getBytes(properties.getCharset()));
    assertEquals(poolConfig.getMinIdle() - 1, pool.getNumIdle());
    assertEquals(1, pool.getNumActive());

    final byte[] response = client.read();

    assertEquals("안녕하세요?", new String(response, properties.getCharset()));
    assertEquals(poolConfig.getMinIdle(), pool.getNumIdle());
    assertEquals(0, pool.getNumActive());
  }

  @Test
    // 동시성 문제가 있지만, 완전 못쓸 수준을 아니라 판단함
    // spring-data-redis 등도 apache.commons.pool2를 사용함
  void pooledTcpClient_underMultiThreads() {
    final TcpClient client = new PooledTcpClient(properties.getHost(), properties.getPort(),
        properties.getCharset(), poolConfig.getMinIdle(), poolConfig.getMaxIdle(),
        poolConfig.getMaxTotal());
    final ObjectPool<Tuple> pool = getPool(client);
    final int noOfTests = poolConfig.getMaxTotal();
    final Executor executor = Executors.newFixedThreadPool(noOfTests);
    final String message = "안녕하세요?\n";

    final List<CompletableFuture<byte[]>> futures = new ArrayList<>();
    for (int i = 0; i < noOfTests; i++) {
      final CompletableFuture<byte[]> future = CompletableFuture.supplyAsync(() -> {
        try {
          log.info("Sending message in a thread, pool state: numIdle={}, numActive={}", pool.getNumIdle(),
              pool.getNumActive());
          client.write(message.getBytes(properties.getCharset()));
          return client.read();
        } catch (Exception e) {
          log.error(e.getMessage());
        }
        return new byte[]{};
      }, executor);

      futures.add(future);
    }

    while (true) {
      final boolean allDone = futures.stream().allMatch(Future::isDone);
      if (allDone) {
        break;
      }
    }

    futures.stream().forEach(f -> {
      try {
        log.info("response: {}", new String(f.get(), properties.getCharset()));
      } catch (Exception e) {
      }
    });

    log.info("All done, pool state: numIdle={}, numActive={}", pool.getNumIdle(), pool.getNumActive());
  }

  ObjectPool<Tuple> getPool(TcpClient client) {
    // private method에 접근하기 위해 Reflection 사용
    final Method method = ReflectionUtils.findMethod(client.getClass(), "getPool").get();
    return (ObjectPool<Tuple>) ReflectionUtils.invokeMethod(method, client);
  }
}
