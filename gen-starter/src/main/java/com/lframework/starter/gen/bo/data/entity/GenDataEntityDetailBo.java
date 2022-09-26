package com.lframework.starter.gen.bo.data.entity;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.service.system.ISysDataDicService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenDataEntityDetailBo extends BaseBo<GenDataEntityDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 字段显示名称
   */
  @ApiModelProperty("字段显示名称")
  private String name;

  /**
   * 字段名称
   */
  @ApiModelProperty("字段名称")
  private String columnName;

  /**
   * 是否主键
   */
  @ApiModelProperty("是否主键")
  private Boolean isKey;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  private Integer dataType;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer columnOrder;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 显示类型
   */
  @ApiModelProperty("显示类型")
  private Integer viewType;

  /**
   * 是否内置枚举
   */
  @ApiModelProperty("是否内置枚举")
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  @ApiModelProperty("后端枚举名")
  private String enumBack;

  /**
   * 前端枚举名
   */
  @ApiModelProperty("前端枚举名")
  private String enumFront;

  /**
   * 正则表达式
   */
  @ApiModelProperty("正则表达式")
  private String regularExpression;

  /**
   * 是否排序字段
   */
  @ApiModelProperty("是否排序字段")
  private Boolean isOrder;

  /**
   * 排序类型
   */
  @ApiModelProperty("排序类型")
  private String orderType;

  /**
   * 数据字典ID
   */
  private String dataDicId;

  /**
   * 数据字典名称
   */
  private String dataDicName;

  /**
   * 长度
   */
  @ApiModelProperty("长度")
  private Long len;

  /**
   * 小数位数
   */
  @ApiModelProperty("小数位数")
  private Integer decimals;

  public GenDataEntityDetailBo() {

  }

  public GenDataEntityDetailBo(GenDataEntityDetail dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataEntityDetail> convert(GenDataEntityDetail dto) {

    return super.convert(dto, GenDataEntityDetailBo::getDataType,
        GenDataEntityDetailBo::getViewType, GenDataEntityDetailBo::getOrderType);
  }

  @Override
  protected void afterInit(GenDataEntityDetail dto) {

    this.dataType = dto.getDataType().getCode();
    this.viewType = dto.getViewType().getCode();
    this.orderType = dto.getOrderType() == null ? null : dto.getOrderType().getCode();

    if (!StringUtil.isBlank(dto.getDataDicId())) {
      ISysDataDicService sysDataDicService = ApplicationUtil.getBean(ISysDataDicService.class);
      SysDataDic dic = sysDataDicService.findById(dto.getDataDicId());
      this.dataDicName = dic.getName();
    }
  }
}