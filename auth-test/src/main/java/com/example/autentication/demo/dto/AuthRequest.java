package com.example.autentication.demo.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class AuthRequest {

  private final String username;
  private final String password;
}
