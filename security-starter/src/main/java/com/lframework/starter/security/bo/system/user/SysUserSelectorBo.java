package com.lframework.starter.security.bo.system.user;

import com.lframework.starter.mybatis.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserSelectorBo extends BaseBo<DefaultSysUserDto> {

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
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysUserSelectorBo() {

  }

  public SysUserSelectorBo(DefaultSysUserDto dto) {

    super(dto);
  }
}
