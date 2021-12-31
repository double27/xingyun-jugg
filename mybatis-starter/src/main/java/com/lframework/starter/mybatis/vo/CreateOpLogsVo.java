package com.lframework.starter.mybatis.vo;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增操作日志Vo
 *
 * @author zmj
 */
@Data
public class CreateOpLogsVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志名称
     */
    @NotBlank(message = "日志名称不能为空！")
    private String name;

    /**
     * 类型
     */
    @NotNull(message = "日志类型不能为空！")
    @IsEnum(message = "日志类型不能为空！", enumClass = OpLogType.class)
    private Integer logType;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * IP地址
     */
    @NotBlank(message = "IP地址不能为空！")
    private String ip;

    /**
     * 补充信息
     */
    private String extra;
}
