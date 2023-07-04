package com.example.autentication.demo;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.example.autentication.demo.dto.TokenResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 8081)
@ActiveProfiles("test")
class AuthIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void should_return_successfully_valid_token_with_date_request() {

    stubFor(
      post(urlPathEqualTo("/token"))
        .willReturn(ok()
          .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withBodyFile("auth_token_ok.json")
        )
    );

    webTestClient
      .get()
      .uri(uriBuilder ->
        uriBuilder.path("/get-token")
          .build())
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody(TokenResponse.class)
      .value(TokenResponse::getToken, Matchers.containsString("test-token"))
      .value(TokenResponse::getDate, Matchers.matchesRegex(
        "^(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s\\d{1,2},\\s\\d{4}$"));
  }
}