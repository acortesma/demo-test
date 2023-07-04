package com.example.autentication.demo.service;

import com.example.autentication.demo.client.AuthClient;
import com.example.autentication.demo.dto.TokenResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
    .withZone(ZoneId.systemDefault());

  private final AuthClient authClient;


  @Override
  public Mono<TokenResponse> getToken() {

    return authClient.login()
      .map(res ->
        TokenResponse.builder()
          .token(res.getToken())
          .date(this.formatDate(Instant.now()))
          .build()
      );
  }

  private String formatDate(Instant instant) {

    return formatter.format(instant);
  }
}
