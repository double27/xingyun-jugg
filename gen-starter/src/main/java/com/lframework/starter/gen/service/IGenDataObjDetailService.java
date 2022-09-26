package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenDataObjDetailService extends BaseMpService<GenDataObjDetail> {

  /**
   * 根据数据对象ID查询
   *
   * @param objId
   * @return
   */
  List<GenDataObjDetail> getByObjId(String objId);

  /**
   * 根据数据对象ID删除
   *
   * @param objId
   */
  void deleteByObjId(String objId);

  /**
   * 实体明细ID是否已关联数据对象
   *
   * @param entityDetailId
   * @return
   */
  Boolean entityDetailIsRela(String entityDetailId);
}
