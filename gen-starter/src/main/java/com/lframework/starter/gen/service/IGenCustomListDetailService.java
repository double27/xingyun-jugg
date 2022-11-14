package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenCustomListDetail;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenCustomListDetailService extends BaseMpService<GenCustomListDetail> {

  /**
   * 根据自定义列表ID查询
   * @param customListId
   * @return
   */
  List<GenCustomListDetail> getByCustomListId(String customListId);

  /**
   * 根据自定义列表ID删除
   * @param customListId
   */
  void deleteByCustomListId(String customListId);

}
