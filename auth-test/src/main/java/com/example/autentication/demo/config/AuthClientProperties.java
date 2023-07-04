package com.example.autentication.demo.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai.auth-client")
@Data
public class AuthClientProperties {

  private String username;
  private String password;
  private String url;
}
