package com.lframework.starter.security.bo.system.tenant;

import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantSelectorBo extends BaseBo<Tenant> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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

  public TenantSelectorBo() {

  }

  public TenantSelectorBo(Tenant dto) {

    super(dto);
  }
}
