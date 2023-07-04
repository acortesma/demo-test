package com.example.autentication.demo.service;

import com.example.autentication.demo.dto.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

  Mono<TokenResponse> getToken();
}
