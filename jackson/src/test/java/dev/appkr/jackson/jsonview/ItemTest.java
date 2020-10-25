package dev.appkr.jackson.jsonview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

  private Logger log = LoggerFactory.getLogger(getClass());

  private static ObjectMapper mapper = new ObjectMapper();
//  static {
//    mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
//  }

  @Test
  public void testSerializationWithoutView() throws JsonProcessingException {
    final Item item = new Item(2, "book", "John");

    String serialized = mapper.writeValueAsString(item);

    log.info("Serialized without Views {}", serialized);
  }

  @Test
  public void testSerializationWithPublicView() throws JsonProcessingException {
    final Item item = new Item(2, "book", "John");

    String serialized = mapper
        .writerWithView(Views.Public.class)
        .writeValueAsString(item);

    log.info("Serialized with Views.Public {}", serialized);
    assertTrue(serialized.contains("book"));
    assertFalse(serialized.contains("John"));
  }

  @Test
  public void testSerializationWithInternalView() throws JsonProcessingException {
    final Item item = new Item(2, "book", "John");

    String serialized = mapper
        .writerWithView(Views.Internal.class)
        .writeValueAsString(item);

    log.info("Serialized with Views.Internal {}", serialized);
    assertTrue(serialized.contains("book"));
    assertTrue(serialized.contains("John"));
  }
}