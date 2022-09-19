package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenUpdateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateColumnConfigListener implements ApplicationListener<DataEntityDeleteEvent> {

  @Autowired
  private IGenUpdateColumnConfigService genUpdateColumnConfigService;

  @Override
  public void onApplicationEvent(DataEntityDeleteEvent event) {

    for (String columnId : event.getColumnIds()) {
      genUpdateColumnConfigService.deleteById(columnId);
    }
  }
}
