package springstudy.querydsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.TreeSet;

public class TagConverter implements AttributeConverter<TreeSet<String>, String> {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TreeSet<String> attribute) {
        String json = null;
        if (attribute.isEmpty()) {
            return json;
        }

        try {
            json = om.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // Ignore exception
        }

        return json;
    }

    @Override
    public TreeSet<String> convertToEntityAttribute(String dbData) {
        TreeSet<String> tags = new TreeSet<>();
        if (dbData == null || StringUtils.isEmpty(dbData)) {
            return tags;
        }

        try {
            tags = om.readValue(dbData, new TypeReference<TreeSet<String>>() {});
        } catch (IOException e) {
            // Ignore exception
        }

        return tags;
    }
}
