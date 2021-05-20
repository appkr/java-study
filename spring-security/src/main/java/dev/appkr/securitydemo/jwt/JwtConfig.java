package dev.appkr.securitydemo.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.jwt", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class JwtConfig {

  public String secretKey;
  public Integer expiresInSeconds;

  public JwtConfig() {
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public Integer getExpiresInSeconds() {
    return expiresInSeconds;
  }

  public void setExpiresInSeconds(Integer expiresInSeconds) {
    this.expiresInSeconds = expiresInSeconds;
  }
}
