package dev.appkr.jackson.jsonview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

  private Logger log = LoggerFactory.getLogger(getClass());

  private static ObjectMapper mapper = new ObjectMapper();
  static {
    // NOTE. It's also important to understand, that – by default – all properties not explicitly marked as being part
    // of a view, are serialized. We are disabling that behavior with the handy DEFAULT_VIEW_INCLUSION feature.
    mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
  }

  @Test
  public void testSerializeUser() throws JsonProcessingException {
    final User john = new User(1, "John");

    final String serialized = mapper
        .writerWithView(Views.Public.class)
        .writeValueAsString(john);

    log.info("Serialized user {}", serialized);
    assertTrue(serialized.contains("John"));
  }

  @Test
  public void testDeserializeUser() throws JsonProcessingException {
    String json = "{\"id\":1,\"name\":\"John\"}";

    final User john = mapper
        .readerWithView(Views.Public.class)
        .forType(User.class)
        .readValue(json);

    log.info("Deserialized User {}", john);
    assertTrue(john.getName().equals("John"));
  }
}