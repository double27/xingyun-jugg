package com.lframework.starter.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统通知记录
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notice_log")
public class SysNoticeLog extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SysNoticeLog";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 标题
   */
  private String noticeId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 是否已读
   */
  private Boolean readed;

  /**
   * 已读时间
   */
  private LocalDateTime readTime;

}
