package com.lframework.starter.security.config;

import com.lframework.starter.security.components.CheckPermissionHandler;
import com.lframework.starter.security.components.CheckPermissionHandlerImpl;
import com.lframework.starter.security.impl.message.TodoTaskServiceImpl;
import com.lframework.starter.security.service.message.ITodoTaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SessionProperties.class)
public class SpringSecurityConfiguration {

  @Bean("permission")
  @ConditionalOnMissingBean(CheckPermissionHandler.class)
  public CheckPermissionHandler checkPermissionHandler() {

    return new CheckPermissionHandlerImpl();
  }

  @Bean
  @ConditionalOnMissingBean(ITodoTaskService.class)
  public ITodoTaskService getTodoTaskService() {

    return new TodoTaskServiceImpl();
  }
}
