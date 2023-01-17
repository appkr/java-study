package dev.appkr.contentnegotiation;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    // NOTE: 유효하지 않은 Accept 헤더를 제출할 경우 @ExceptionHandler, @ControllerAdvice에
    //       도달하기 전에 예외가 발생함; 이를 해결하기 위해 프레임웍의 Accept 헤더 검증을 무시하고,
    //       컨트롤러에서 Accept 헤더 유효성 검사를 수행함
    // @see com.vroong.pointchargercallback.presentation.KisHttpCallbackHandler.handle
    configurer.favorParameter(false)                            // 요청 파라미터에 미디어 타입을 정의하는 값이 포함되어 있다면 그 값을 미디어 타입으로 사용 e.g. /?format=json
        .ignoreAcceptHeader(true)                               // false이면, 요청 파라미터가 없을 때 Accept 헤더를 이용
        .defaultContentType(MediaType.APPLICATION_JSON);        // Accept 헤더도 없다면 이 값을 기본 미디어 타입으로 사용
  }
}
