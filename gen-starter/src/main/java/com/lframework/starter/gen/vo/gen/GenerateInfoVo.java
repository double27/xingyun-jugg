package com.lframework.starter.gen.vo.gen;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenerateInfoVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 生成模板类型
   */
  @ApiModelProperty("生成模板类型")
  private Integer templateType;

  /**
   * 包名
   */
  @ApiModelProperty("包名")
  private String packageName;

  /**
   * 模块名称
   */
  @ApiModelProperty("模块名称")
  private String moduleName;

  /**
   * 业务名称
   */
  @ApiModelProperty("业务名称")
  private String bizName;
}
