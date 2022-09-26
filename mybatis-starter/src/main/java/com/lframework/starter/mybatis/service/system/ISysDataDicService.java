package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.dic.CreateSysDataDicVo;
import com.lframework.starter.mybatis.vo.system.dic.QuerySysDataDicVo;
import com.lframework.starter.mybatis.vo.system.dic.SysDataDicSelectorVo;
import com.lframework.starter.mybatis.vo.system.dic.UpdateSysDataDicVo;
import java.util.List;

/**
 * 数据字典 Service
 *
 * @author zmj
 */
public interface ISysDataDicService extends BaseMpService<SysDataDic> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysDataDic> query(Integer pageIndex, Integer pageSize, QuerySysDataDicVo vo);

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDic> query(QuerySysDataDicVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysDataDic> selector(Integer pageIndex, Integer pageSize, SysDataDicSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysDataDic findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysDataDicVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysDataDicVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}