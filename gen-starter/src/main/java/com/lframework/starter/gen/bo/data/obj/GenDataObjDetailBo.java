package com.lframework.starter.gen.bo.data.obj;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenDataObjDetailBo extends BaseBo<GenDataObjDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 主表字段
   */
  @ApiModelProperty("主表字段")
  private String[] mainTableDetailIds;

  /**
   * 关联类型
   */
  @ApiModelProperty("关联类型")
  private Integer relaType;

  /**
   * 关联方式
   */
  @ApiModelProperty("关联方式")
  private Integer relaMode;

  /**
   * 子表ID
   */
  @ApiModelProperty("子表ID")
  private String subTableId;

  /**
   * 子表名称
   */
  @ApiModelProperty("子表名称")
  private String subTableName;

  /**
   * 子表别名
   */
  @ApiModelProperty("子表别名")
  private String subTableAlias;

  /**
   * 子表字段
   */
  @ApiModelProperty("子表字段")
  private String[] subTableDetailIds;

  public GenDataObjDetailBo() {

  }

  public GenDataObjDetailBo(GenDataObjDetail dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataObjDetail> convert(GenDataObjDetail dto) {

    return super.convert(dto, GenDataObjDetailBo::getRelaType, GenDataObjDetailBo::getRelaMode,
        GenDataObjDetailBo::getMainTableDetailIds, GenDataObjDetailBo::getSubTableDetailIds);
  }

  @Override
  protected void afterInit(GenDataObjDetail dto) {

    this.relaType = dto.getRelaType().getCode();
    this.relaMode = dto.getRelaMode().getCode();
    this.mainTableDetailIds = dto.getMainTableDetailIds().split(StringPool.STR_SPLIT);
    this.subTableDetailIds = dto.getSubTableDetailIds().split(StringPool.STR_SPLIT);

    IGenDataEntityService genDataEntityService = ApplicationUtil.getBean(
        IGenDataEntityService.class);
    GenDataEntity entity = genDataEntityService.findById(dto.getSubTableId());
    this.subTableName = entity.getName();
  }
}
