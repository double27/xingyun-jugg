package com.lframework.starter.security.dto.system.role;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DefaultSysRoleDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "DefaultSysRoleDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 权限
   */
  private String permission;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
