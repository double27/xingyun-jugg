package com.lframework.starter.security.bo.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpLogInUserCenterBo extends BaseBo<DefaultOpLogsDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 日志名称
     */
    private String name;

    /**
     * 类别
     */
    private Integer logType;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public OpLogInUserCenterBo() {

    }

    public OpLogInUserCenterBo(DefaultOpLogsDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<DefaultOpLogsDto> convert(DefaultOpLogsDto dto) {

        return super.convert(dto, OpLogInUserCenterBo::getLogType);
    }

    @Override
    protected void afterInit(DefaultOpLogsDto dto) {

        this.logType = dto.getLogType().getCode();
    }
}
