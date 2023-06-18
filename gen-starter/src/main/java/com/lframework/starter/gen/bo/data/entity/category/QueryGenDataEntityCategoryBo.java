package com.lframework.starter.gen.bo.data.entity.category;

import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenDataEntityCategoryBo extends BaseBo<GenDataEntityCategory> {

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


  public QueryGenDataEntityCategoryBo() {

  }

  public QueryGenDataEntityCategoryBo(GenDataEntityCategory dto) {

    super(dto);
  }
}
