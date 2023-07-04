package com.example.autentication.demo.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RestClientTest(value = AuthClient.class)
@Testcontainers
@ActiveProfiles("test")
class AuthClientContainerTest {

  @Autowired
  private AuthClient authClient;

  @Container
  static final DockerComposeContainer<?> container = new DockerComposeContainer<>(
    new File("src/test/resources/test-compose.yml"));

  @Test
  void should_return_unauthorized_if_credentials_are_not_valid() {

    var response = Mono.just("")
      .delayElement(Duration.ofSeconds(3))
      .then(authClient.login());

    response
      .as(StepVerifier::create)
      .verifyErrorSatisfies(error -> {
        assertThat(error).isInstanceOf(WebClientResponseException.Unauthorized.class);
        assertThat(error).hasMessageContainingAll("401", "Unauthorized", "POST", "/token");
      });
  }

}