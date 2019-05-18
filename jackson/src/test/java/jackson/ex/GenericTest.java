package jackson.ex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GenericTest {
    private ObjectMapper om = new ObjectMapper();

    @Test
    public void shouldHydrateToMapObject() {
        String json = "{\"name\": \"Bob\"}";
        Map<String, String> myMap = null;

        try {
            myMap = om.readValue(json, Map.class);
        } catch (IOException e) { }

        assertTrue(myMap.get("name").equals("Bob"));
    }

    @Test
    public void shouldHydrateToListOfStringObject() {
        String json = "[\"Alice\", \"Bob\", \"Charlie\"]";
        List<String> myList = null;

        try {
            myList = om.readValue(json, List.class);
        } catch (IOException e) { }

        assertTrue(myList.get(0).equals("Alice"));
    }

    @Test
    public void shouldHydrateFromComplexJson() {
        String json = "{\"data\": [{\"name\": \"Alice\", \"age\": 13}, {\"name\": \"Bob\", \"age\": 13}], \"meta\": {\"count\": 2}}";
        Map<String, Object> myMap = null;

        try {
            myMap = om.readValue(json, Map.class);
        } catch (IOException e) { }

        List<?> dataList = (List<?>) myMap.get("data");
        assertTrue(dataList.size() == 2);

        Map<String, String> dataElem = (Map<String, String>) dataList.get(0);
        assertTrue(dataElem.get("name").equals("Alice"));
    }

    @Test
    public void shouldHydrateToNestedMap() {
        String json = "{\"data\": [{\"name\": \"Alice\", \"age\": 13}, {\"name\": \"Bob\", \"age\": 13}]}";
        Map<String, List<MyValue>> myMap = null;

        try {
            // TypeReference는 추상클래스이므로 { }를 붙여서 구현해줘야 함
            myMap = om.readValue(json, new TypeReference<Map<String, List<MyValue>>>() { });
        } catch (IOException e) { }

        List<MyValue> dataList = myMap.get("data");
        assertTrue(dataList.get(0).getName().equals("Alice"));
    }

    @Test
    public void shouldHydrateJsonArray() {
        String json = "[{\"name\": \"Alice\", \"age\": 13}, {\"name\": \"Bob\", \"age\": 13}]";
        List<MyValue> myList = null;

        try {
            myList = om.readValue(json, new TypeReference<List<MyValue>>() {});
        } catch (IOException e) { }

        assertTrue(myList.get(0).getName().equals("Alice"));
    }
}
