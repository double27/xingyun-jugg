package com.lframework.starter.mybatis.config;

import com.lframework.starter.mybatis.impl.DefaultOpLogsServiceImpl;
import com.lframework.starter.mybatis.service.IOpLogsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志配置
 *
 * @author zmj
 */
@Configuration
public class OpLogConfiguration {

  public static final String OP_LOG_THREAD_POOL_NAME = "opLogThreadPool";

  @Bean
  @ConditionalOnMissingBean(IOpLogsService.class)
  public IOpLogsService getOpLogsService() {

    return new DefaultOpLogsServiceImpl();
  }
}
