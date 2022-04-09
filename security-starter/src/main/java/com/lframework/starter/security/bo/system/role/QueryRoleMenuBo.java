package com.lframework.starter.security.bo.system.role;

import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRoleMenuBo extends BaseBo<DefaultSysMenuDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 标题
   */
  private String title;

  /**
   * 类型 0-目录 1-菜单 2-功能
   */
  private Integer display;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 权限
   */
  private String permission;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 是否选择
   */
  private Boolean selected = Boolean.FALSE;

  public QueryRoleMenuBo() {

  }

  public QueryRoleMenuBo(DefaultSysMenuDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DefaultSysMenuDto> convert(DefaultSysMenuDto dto) {

    return super.convert(dto, QueryRoleMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(DefaultSysMenuDto dto) {

    this.display = dto.getDisplay().getCode();
  }
}
