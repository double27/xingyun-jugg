package com.lframework.starter.security.bo.system.dic.category;

import com.lframework.starter.mybatis.entity.SysDataDicCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysDataDicCategoryBo extends BaseBo<SysDataDicCategory> {

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


  public QuerySysDataDicCategoryBo() {

  }

  public QuerySysDataDicCategoryBo(SysDataDicCategory dto) {

    super(dto);
  }
}
