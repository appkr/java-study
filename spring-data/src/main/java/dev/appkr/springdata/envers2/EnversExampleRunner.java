package dev.appkr.springdata.envers2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnversExampleRunner implements CommandLineRunner {

  private final EnversExampleCreator creator;
  private final EnversExampleReader reader;
  private final EnversExampleUpdater updater;
  private final EnversExampleDeleter deleter;

  @Override
  public void run(String... args) throws Exception {
    creator.run();
    updater.run();
    deleter.run();
    reader.read(1);
  }
}
