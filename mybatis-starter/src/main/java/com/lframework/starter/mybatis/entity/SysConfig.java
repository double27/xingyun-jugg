package com.lframework.starter.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 是否允许注册
   */
  private Boolean allowRegist;

  /**
   * 是否允许锁定用户
   */
  private Boolean allowLock;

  /**
   * 登录失败次数
   */
  private Integer failNum;

  /**
   * 是否允许验证码
   */
  private Boolean allowCaptcha;

  /**
   * 是否开启忘记密码
   */
  private Boolean allowForgetPsw;

  /**
   * 忘记密码是否使用邮箱
   */
  private Boolean forgetPswRequireMail;

  /**
   * 忘记密码是否使用短信
   */
  private Boolean forgetPswRequireSms;

  /**
   * signName
   */
  private String signName;

  /**
   * templateCode
   */
  private String templateCode;

}
