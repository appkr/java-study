package dev.appkr.securitydemo.jwt;

public class AccessToken {
  private String accessToken;
  private String tokenType = "bearer";
  private Long iat;
  private Long expiresIn;
  private String scope = "openid";
  private String jti;

  public AccessToken(String accessToken, Long iat, Long expiresIn, String jti) {
    this.accessToken = accessToken;
    this.iat = iat;
    this.expiresIn = expiresIn;
    this.jti = jti;
  }

  public AccessToken() {
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public Long getIat() {
    return iat;
  }

  public void setIat(Long iat) {
    this.iat = iat;
  }

  public Long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getJti() {
    return jti;
  }

  public void setJti(String jti) {
    this.jti = jti;
  }
}
