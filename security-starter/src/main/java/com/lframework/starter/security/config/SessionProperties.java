package com.lframework.starter.security.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Session配置信息
 *
 * @author zmj
 */
@ConfigurationProperties(prefix = "session")
public class SessionProperties {

  /**
   * 同一个用户最多同时在线数量
   */
  private Integer maximumSessions = 1;

  /**
   * 达到最多用户数后是否保持在线
   */
  private Boolean maxSessionsPreventsLogin = false;

  /**
   * Token Secret
   */
  private String tokenSecret;

  /**
   * Token发行人
   */
  private String issuer;

  public Integer getMaximumSessions() {

    return maximumSessions;
  }

  public void setMaximumSessions(Integer maximumSessions) {

    this.maximumSessions = maximumSessions;
  }

  public Boolean getMaxSessionsPreventsLogin() {

    return maxSessionsPreventsLogin;
  }

  public void setMaxSessionsPreventsLogin(Boolean maxSessionsPreventsLogin) {

    this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
  }

  public String getTokenSecret() {
    return tokenSecret;
  }

  public void setTokenSecret(String tokenSecret) {
    this.tokenSecret = tokenSecret;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
}
