package com.example.autentication.demo.client;


import com.example.autentication.demo.config.AuthClientProperties;
import com.example.autentication.demo.dto.AuthRequest;
import com.example.autentication.demo.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@EnableConfigurationProperties(AuthClientProperties.class)
public class AuthClient {

  private static final String CLIENT_NAME = "auth";
  private static final String ERROR_MESSAGE = "Error calling " + CLIENT_NAME;
  private final WebClient webClient;

  private final AuthClientProperties authClientProperties;

  public AuthClient(WebClient.Builder webClientBuilder, AuthClientProperties authClientProperties) {

    this.authClientProperties = authClientProperties;
    this.webClient = webClientBuilder.baseUrl(authClientProperties.getUrl()).build();
  }

  public Mono<AuthResponse> login() {

    var authRequest = AuthRequest.of(authClientProperties.getUsername(), authClientProperties.getPassword());

    return webClient
      .post()
      .uri(uriBuilder -> uriBuilder
        .path("/token")
        .build())
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(authRequest)
      .retrieve()
      .bodyToMono(AuthResponse.class)
      .doOnNext(a -> log.info("response: {}", a))
      .doOnError(error -> log.error(ERROR_MESSAGE, error));
  }
}
