package dev.appkr.reactiveweb.webclient;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;
import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.logging.LogLevel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Component
@Slf4j
public class WebClientRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    getHeaders();
    postJsonBody();
//    reqResLogging(); // NOT WORKING -> org.springframework.core.codec.DecodingException: JSON decoding error: Unrecognized field "Accept" (class dev.appkr.reactiveweb.webclient.WebClientRunner$Headers), not marked as ignorable; nested exception is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "Accept" (class dev.appkr.reactiveweb.webclient.WebClientRunner$Headers), not marked as ignorable (one known property: "headers"])
    bearerAuth();
//    statusCodeHandling();
    exchange();
    postFormData();
//    postMultipartData();
  }

  void getHeaders() {
    final WebClient client = WebClient.builder().build();
    final Mono<Headers> headerMono = client
        .get()
        .uri("https://httpbin.org/headers")
        .accept(APPLICATION_JSON)
        .header("foo", "bar")
        .retrieve()
        .bodyToMono(Headers.class);

    headerMono.subscribe(header -> log.info("getHeaders: {}", header));
  }

  void postJsonBody() {
    // request response log
    HttpClient httpClient = HttpClient.create()
        .wiretap(this.getClass().getCanonicalName(), LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);
    ClientHttpConnector conn = new ReactorClientHttpConnector(httpClient);

    final WebClient client =  WebClient.builder()
        .clientConnector(conn)
        .build();

    final Mono<Response> responseMono = client
        .post()
        .uri("https://httpbin.org/post")
        .contentType(APPLICATION_JSON)
        .bodyValue(new Dummy())
        .retrieve()
        .bodyToMono(Response.class);

    responseMono.subscribe(response -> log.info("postJsonBody: {}", response));
  }

  void reqResLogging() {
    final ObjectMapper om = new ObjectMapper();
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(configurer -> {
          configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(om, APPLICATION_JSON));
          configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(om, APPLICATION_JSON));
        })
        .build();

    final WebClient client =  WebClient.builder()
        .exchangeStrategies(strategies)
        .filters(filtersConsumer -> {
          filtersConsumer.add(logRequest());
          filtersConsumer.add(logResponse());
        })
        .build();

    final Mono<Response> responseMono = client
        .post()
        .uri("https://httpbin.org/post")
        .contentType(APPLICATION_JSON)
        .bodyValue(new Dummy())
        .retrieve()
        .bodyToMono(Response.class);

    responseMono.subscribe(response -> log.info("reqResLogging: {}", response));
  }

    ExchangeFilterFunction logRequest() {
      final ObjectMapper om = new ObjectMapper();
      Map<String, Object> map = Collections.emptyMap();
      map.put("baz", "qux");
      return ExchangeFilterFunction.ofRequestProcessor(req -> {
        try {
          log.info(om.writeValueAsString(map));
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return Mono.just(req);
      });
    }

    ExchangeFilterFunction logResponse() {
      final ObjectMapper om = new ObjectMapper();
      Map<String, Object> map = Collections.emptyMap();
      map.put("baz", "qux");

      return ExchangeFilterFunction.ofResponseProcessor(res -> {
        try {
          log.info(om.writeValueAsString(map));
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return Mono.just(res);
      });
    }

  void bearerAuth() {
    final WebClient client = WebClient.builder().build();
    final Mono<Authentication> responseMono = client
        .get()
        .uri("https://httpbin.org/bearer")
        .accept(APPLICATION_JSON)
        .header("Authorization", "Bearer header.body.signature")
        .retrieve()
        .bodyToMono(Authentication.class);

    responseMono.subscribe(response -> log.info("bearerAuth: {}", response), error -> log.error("bearerAuth: {}", error.getMessage()));
  }

  void statusCodeHandling() {
    final WebClient client = WebClient.builder().build();
    final Mono<Void> voidMono = client
        .get()
        .uri("https://httpbin.org/status/{status}", "500")
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, res -> Mono.error(new RuntimeException("Client error")))
        .onStatus(HttpStatus::is5xxServerError, res -> Mono.error(new RuntimeException("Server error")))
        .bodyToMono(Void.class);

    voidMono.subscribe();
  }

  void exchange() {
    final WebClient client = WebClient.create();
    final Mono<Response> responseMono = client
        .post()
        .uri("https://httpbin.org/post")
        .contentType(APPLICATION_JSON)
        .bodyValue(new Dummy())
        .exchangeToMono(res -> res.bodyToMono(Response.class));

    responseMono.subscribe(response -> log.info("exchange: {}", response));
    // retrieve()와는 다르게 exchange()는 모든 시나리오에서(성공, 오류, 예기치 못한 데이터 등) 어플리케이션이 직접 response body를 컨슘해야 한다.
    // 그렇지 않으면 메모리 릭이 발생할 수 있다.
    // exchange()를 사용해서 응답 코드나 헤더를 봐야 로직을 결정할 수 있다거나, 아니면 직접 응답을 컨슘해야 한다거나 하는 특별한 이유가 없다면 retrieve()를 쓰는 게 좋다.
  }

  void postFormData() {
    MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
    formData.add("foo", "bar");

    final Mono<Response> responseMono = WebClient.create()
        .post()
        .uri("https://httpbin.org/post")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .bodyValue(formData)
        .body(fromFormData("k1", "v1").with("k2", "v2"))
        .retrieve()
        .bodyToMono(Response.class);

    responseMono.subscribe(response -> log.info("postFormData: {}", response));
  }

  void postMultipartData() {
    final Resource resource = new FileSystemResource(
        "/Users/juwon.kim/Desktop/T5EE2GQBA-U5SQ1HK8R-gd97e738bf1e-512.png");

    // Caused by: org.springframework.web.reactive.function.client.WebClientResponseException:
    // 200 OK from POST https://httpbin.org/post; nested exception is org.springframework.core.io.buffer.DataBufferLimitException: Exceeded limit on max bytes to buffer : 262144
    WebClient client = WebClient.builder()
        .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)
        )
        .build();

    final Mono<Response> responseMono = client
        .post()
        .uri("https://httpbin.org/post")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(fromMultipartData("fieldPart", "value").with("filePart", resource))
        .retrieve()
        .bodyToMono(Response.class);

    responseMono.subscribe(response -> log.info("postMultipartData: {}", response));
  }

  @Getter @Setter @ToString
  static class Response {
    Object args;
    Object data;
    Object files;
    Object form;
    Headers headers;
    Dummy json;
    Object origin;
    Object url;
  }

  @Getter @Setter @ToString
  static class Headers {
    Map<String, Object> headers = new HashMap<>();
  }

  @Getter @Setter @ToString
  static class Dummy {
    String foo = "bar";
  }

  @Getter @Setter @ToString
  static class Authentication {
    Boolean authenticated;
    String token;
  }
}
