package com.example.autentication.demo.client;

import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;


@RestClientTest(value = AuthClient.class)
@AutoConfigureWireMock(port = 8081)
@ActiveProfiles("test")
class AuthClientTest {

  @Autowired
  private AuthClient authClient;

  @Test
  void name() {

    stubFor(
      post(urlPathEqualTo("/token"))
        .withRequestBody(matchingJsonPath("$.username", WireMock.equalTo("name")))
        .withRequestBody(matchingJsonPath("$.password", WireMock.equalTo("pass")))
        .willReturn(ok()
          .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withBodyFile("auth_token_ok.json")
        )
    );

    var response = authClient.login();

    response
      .as(StepVerifier::create)
      .assertNext(resToken -> Assertions.assertThat(resToken.getToken()).contains("test"))
      .verifyComplete();
  }
}