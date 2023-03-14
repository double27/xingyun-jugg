package com.lframework.starter.security.bo.system.menu;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenCustomForm;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.service.GenCustomFormService;
import com.lframework.starter.gen.service.GenCustomListService;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.enums.system.SysMenuComponentType;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysMenuBo extends BaseBo<DefaultSysMenu> {

  private static final long serialVersionUID = 1L;

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
   * 名称（前端使用）
   */
  @ApiModelProperty("名称（前端使用）")
  private String name;

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
   * 组件类型
   */
  @ApiModelProperty("组件类型（前端使用）")
  private Integer componentType;

  /**
   * 组件（前端使用）
   */
  @ApiModelProperty("组件（前端使用）")
  private String component;

  /**
   * 自定义列表ID
   */
  @ApiModelProperty("自定义列表ID")
  private String customListId;

  /**
   * 自定义列表名称
   */
  @ApiModelProperty("自定义列表名称")
  private String customListName;

  /**
   * 自定义表单ID
   */
  @ApiModelProperty("自定义表单ID")
  private String customFormId;

  /**
   * 自定义表单名称
   */
  @ApiModelProperty("自定义表单名称")
  private String customFormName;

  /**
   * 自定义请求参数
   */
  @ApiModelProperty("自定义请求参数")
  private String requestParam;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 父级名称
   */
  @ApiModelProperty("父级名称")
  private String parentName;

  /**
   * 路由路径（前端使用）
   */
  @ApiModelProperty("路由路径（前端使用）")
  private String path;

  /**
   * 是否缓存（前端使用）
   */
  @ApiModelProperty("是否缓存（前端使用）")
  private Boolean noCache;

  /**
   * 类型 0-目录 1-菜单 2-功能
   */
  @ApiModelProperty("类型 0-目录 1-菜单 2-功能")
  private Integer display;

  /**
   * 是否隐藏（前端使用）
   */
  @ApiModelProperty("是否隐藏（前端使用）")
  private Boolean hidden;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 是否特殊菜单
   */
  @ApiModelProperty("是否特殊菜单")
  private Boolean isSpecial;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysMenuBo() {

  }

  public GetSysMenuBo(DefaultSysMenu dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DefaultSysMenu> convert(DefaultSysMenu dto) {

    return super.convert(dto, GetSysMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(DefaultSysMenu dto) {

    this.display = dto.getDisplay().getCode();
    if (!StringUtil.isBlank(dto.getParentId())) {
      SysMenuService sysMenuService = ApplicationUtil.getBean(SysMenuService.class);
      this.parentName = sysMenuService.findById(dto.getParentId()).getTitle();
    }

    this.componentType = dto.getComponentType() == null ? null : dto.getComponentType().getCode();
    if (dto.getComponentType() == SysMenuComponentType.CUSTOM_LIST) {
      this.customListId = dto.getComponent();
      GenCustomListService genCustomListService = ApplicationUtil
          .getBean(GenCustomListService.class);
      GenCustomList customList = genCustomListService.findById(dto.getComponent());
      this.customListName = customList.getName();
    } else if (dto.getComponentType() == SysMenuComponentType.CUSTOM_FORM) {
      this.customFormId = dto.getComponent();
      GenCustomFormService genCustomFormService = ApplicationUtil
          .getBean(GenCustomFormService.class);
      GenCustomForm customForm = genCustomFormService.findById(dto.getComponent());
      this.customFormName = customForm.getName();
      this.requestParam = dto.getRequestParam();
    }
  }
}
