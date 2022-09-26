package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.gen.entity.GenUpdateColumnConfig;
import com.lframework.starter.gen.vo.gen.UpdateUpdateColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenUpdateColumnConfigService extends BaseMpService<GenUpdateColumnConfig> {

  /**
   * 根据数据实体ID查询
   *
   * @param entityId
   * @return
   */
  List<GenUpdateColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateUpdateColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenUpdateColumnConfigDto findById(String id);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}