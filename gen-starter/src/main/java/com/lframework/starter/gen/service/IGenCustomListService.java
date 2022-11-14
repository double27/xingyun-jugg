package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.vo.custom.list.CreateGenCustomListVo;
import com.lframework.starter.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.starter.gen.vo.custom.list.UpdateGenCustomListVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenCustomListService extends BaseMpService<GenCustomList> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomList> query(Integer pageIndex, Integer pageSize, QueryGenCustomListVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomList> query(QueryGenCustomListVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomList findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenCustomListVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenCustomListVo data);

  /**
   * 根据ID删除
   * @param id
   */
  void delete(String id);

  /**
   * 批量删除
   * @param ids
   */
  void batchDelete(List<String> ids);

  /**
   * 批量启用
   *
   * @param ids
   */
  void batchEnable(List<String> ids);

  /**
   * 批量停用
   *
   * @param ids
   */
  void batchUnable(List<String> ids);

  /**
   * 查询所有关联了数据对象的自定义列表ID
   * @return
   */
  List<String> getRelaGenDataObjIds(String objId);

  /**
   * 查询所有关联了数据实体的自定义列表ID
   * @return
   */
  List<String> getRelaGenDataEntityIds(String entityId);
}
