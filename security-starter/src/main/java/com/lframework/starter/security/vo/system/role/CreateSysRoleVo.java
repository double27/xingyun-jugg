package com.lframework.starter.security.vo.system.role;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysRoleVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 权限
   */
  private String permission;

  /**
   * 备注
   */
  private String description;
}
