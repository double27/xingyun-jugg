package com.lframework.starter.web.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 用户登录Dto
 *
 * @author zmj
 */
@Data
public class LoginDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Token
   */
  @ApiModelProperty("Token")
  private String token;

  /**
   * 用户信息
   */
  @ApiModelProperty("用户信息")
  private UserInfo user;

  /**
   * 角色
   */
  @ApiModelProperty("角色")
  private Set<String> roles;

  public LoginDto(String token, String name, Set<String> roles) {

    this.token = token;
    this.setRoles(roles);

    LoginDto.UserInfo userInfo = new LoginDto.UserInfo();
    userInfo.setName(name);
    this.setUser(userInfo);
  }

  @Data
  public static class UserInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
  }
}
