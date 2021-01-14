package dev.appkr.springdata.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.geo.Point;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class PointConverter implements AttributeConverter<Point, String> {

  private ObjectMapper om = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Point attribute) {
    String json = null;
    try {
      json = om.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      //
    }
    return json;
  }

  @Override
  public Point convertToEntityAttribute(String dbData) {
    Point point = null;
    try {
      point = om.readValue(dbData, Point.class);
    } catch (IOException e) {
      //
    }

    return point;
  }
}
