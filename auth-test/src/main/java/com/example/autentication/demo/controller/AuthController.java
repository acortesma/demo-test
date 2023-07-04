package com.example.autentication.demo.controller;

import com.example.autentication.demo.dto.TokenResponse;
import com.example.autentication.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @GetMapping("/get-token")
  public Mono<TokenResponse> getToken() {
    log.info("Retrieve token");
    return authService.getToken();
  }
}
