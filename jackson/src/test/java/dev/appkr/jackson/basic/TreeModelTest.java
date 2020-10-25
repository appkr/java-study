package dev.appkr.jackson.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeModelTest {

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void shouldReadJsonObjectWithJsonNode() {
        String json = "{\"name\":\"Bob\", \"age\":13}";
        JsonNode root = null;

        try {
            root = om.readTree(json);
        } catch (IOException e) { }

        assertTrue(root.get("name").asText().equals("Bob"));
        assertTrue(root.get("age").asInt() == 13);
    }

    @Test
    public void shouldReadJsonArrayAsArrayNode() {
        String json = "[{\"name\": \"Alice\", \"age\": 13}, {\"name\": \"Bob\", \"age\": 13}]";
        ArrayNode root = null;

        try {
            root = (ArrayNode) om.readTree(json);
        } catch (IOException e) { }

        assertTrue(root.get(0).get("name").asText().equals("Alice"));
    }
}
