package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenQueryParamsColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueryParamsColumnConfigListener implements ApplicationListener<DataEntityDeleteEvent> {

  @Autowired
  private IGenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

  @Override
  public void onApplicationEvent(DataEntityDeleteEvent event) {

    for (String columnId : event.getColumnIds()) {
      genQueryParamsColumnConfigService.deleteById(columnId);
    }
  }
}
