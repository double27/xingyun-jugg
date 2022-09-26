package com.lframework.starter.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据对象自定义查询明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_data_obj_query_detail")
public class GenDataObjQueryDetail extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "GenDataObjQueryDetail";

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String dataObjId;

  /**
   * 显示名称
   */
  private String customName;

  /**
   * 自定义SQL
   */
  private String customSql;

  /**
   * 别名
   */
  private String customAlias;

  /**
   * 排序
   */
  private Integer orderNo;
}
