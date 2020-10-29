package dev.appkr.jackson.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyValueTest {

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void shouldHydrateMyValueObjectFromJsonString() {
        String json = "{\"name\":\"Bob\", \"age\":13}";
        MyValue myValue = null;

        try {
            myValue = om.readValue(json, MyValue.class);
        } catch (IOException e) {}

        assertTrue(myValue instanceof MyValue);
    }

    @Test
    public void shouldSerializeToJsonStringFromMyValueObject() {
        MyValue myValue = new MyValue("Bob", 13);
        String json = null;

        try {
            json = om.writeValueAsString(myValue);
        } catch (JsonProcessingException e) { }

        assertTrue(json.equals("{\"name\":\"Bob\",\"age\":13}"));
    }
}
