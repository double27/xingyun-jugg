package com.lframework.starter.security.bo.system.role;

import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryRoleMenuBo extends BaseBo<DefaultSysMenu> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 图标
   */
  @ApiModelProperty("图标")
  private String icon;

  /**
   * 类型
   */
  @ApiModelProperty("类型")
  private Integer display;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否选择
   */
  @ApiModelProperty("是否选择")
  private Boolean selected = Boolean.FALSE;

  public QueryRoleMenuBo() {

  }

  public QueryRoleMenuBo(DefaultSysMenu dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DefaultSysMenu> convert(DefaultSysMenu dto) {

    return super.convert(dto, QueryRoleMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(DefaultSysMenu dto) {

    this.display = dto.getDisplay().getCode();
  }
}
