package com.lframework.starter.security.bo.system.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryOpLogBo extends BaseBo<DefaultOpLogs> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 日志名称
   */
  @ApiModelProperty("日志名称")
  private String name;

  /**
   * 类别
   */
  @ApiModelProperty("类别")
  private Integer logType;

  /**
   * IP地址
   */
  @ApiModelProperty("IP地址")
  private String ip;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryOpLogBo() {

  }

  public QueryOpLogBo(DefaultOpLogs dto) {

    super(dto);
  }
}
