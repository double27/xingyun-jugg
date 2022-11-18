package com.lframework.starter.gen.listeners;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.entity.GenCustomListDetail;
import com.lframework.starter.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.starter.gen.events.DataObjDeleteEvent;
import com.lframework.starter.gen.events.DataObjQueryDetailDeleteEvent;
import com.lframework.starter.gen.service.IGenCustomListDetailService;
import com.lframework.starter.gen.service.IGenCustomListQueryParamsService;
import com.lframework.starter.gen.service.IGenCustomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenCustomListListener {

  @Component
  public static class DataObjDeleteListener implements ApplicationListener<DataObjDeleteEvent> {

    @Autowired
    private IGenCustomListService genCustomListService;

    @Override
    public void onApplicationEvent(DataObjDeleteEvent event) {

      Wrapper<GenCustomList> queryWrapper = Wrappers.lambdaQuery(GenCustomList.class)
          .eq(GenCustomList::getDataObjId, event.getId());
      if (genCustomListService.count(queryWrapper) > 0) {
        throw new DefaultClientException("数据对象【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }

  @Component
  public static class DataEntityDetailDeleteListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private IGenCustomListDetailService genCustomListDetailService;

    @Autowired
    private IGenCustomListQueryParamsService genCustomListQueryParamsService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      Wrapper<GenCustomListDetail> queryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListDetail.class).eq(GenCustomListDetail::getDataEntityId, event.getId());
      if (genCustomListDetailService.count(queryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }

      Wrapper<GenCustomListQueryParams> queryQueryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListQueryParams.class).eq(GenCustomListQueryParams::getRelaId, event.getId());
      if (genCustomListQueryParamsService.count(queryQueryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }

  @Component
  public static class DataObjQueryDetailDeleteListener implements
      ApplicationListener<DataObjQueryDetailDeleteEvent> {

    @Autowired
    private IGenCustomListDetailService genCustomListDetailService;

    @Autowired
    private IGenCustomListQueryParamsService genCustomListQueryParamsService;

    @Override
    public void onApplicationEvent(DataObjQueryDetailDeleteEvent event) {
      Wrapper<GenCustomListDetail> queryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListDetail.class).eq(GenCustomListDetail::getDataEntityId, event.getId());
      if (genCustomListDetailService.count(queryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }

      Wrapper<GenCustomListQueryParams> queryQueryDetailWrapper = Wrappers.lambdaQuery(
          GenCustomListQueryParams.class).eq(GenCustomListQueryParams::getRelaId, event.getId());
      if (genCustomListQueryParamsService.count(queryQueryDetailWrapper) > 0) {
        throw new DefaultClientException("字段【" + event.getName() + "】已关联自定义列表，无法删除！");
      }
    }
  }
}