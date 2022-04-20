package com.lframework.starter.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu")
public class DefaultSysRoleMenu extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 角色ID
   */
  private String roleId;

  /**
   * 菜单ID
   */
  private String menuId;


}
