package dev.appkr.reactiveweb.errorhandling;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
@Getter
@Setter
public class GlobalErrorAttributes extends DefaultErrorAttributes {

  private HttpStatus status = HttpStatus.BAD_REQUEST;
  private String message = "please provide a name";

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
    Map<String, Object> map = super.getErrorAttributes(request, options);
    map.put("status", status);
    map.put("message", message);
    return map;
  }
}
