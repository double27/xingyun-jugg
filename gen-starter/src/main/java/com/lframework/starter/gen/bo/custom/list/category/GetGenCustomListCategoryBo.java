package com.lframework.starter.gen.bo.custom.list.category;

import com.lframework.starter.gen.entity.GenCustomListCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetGenCustomListCategoryBo extends BaseBo<GenCustomListCategory> {

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


  public GetGenCustomListCategoryBo() {

  }

  public GetGenCustomListCategoryBo(GenCustomListCategory dto) {

    super(dto);
  }
}
