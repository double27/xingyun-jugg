package com.lframework.starter.security.bo.system.position;

import com.lframework.starter.mybatis.entity.DefaultSysPosition;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysPositionSelectorBo extends BaseBo<DefaultSysPosition> {

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
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysPositionSelectorBo() {

  }

  public SysPositionSelectorBo(DefaultSysPosition dto) {

    super(dto);
  }

  @Override
  protected void afterInit(DefaultSysPosition dto) {

  }
}
