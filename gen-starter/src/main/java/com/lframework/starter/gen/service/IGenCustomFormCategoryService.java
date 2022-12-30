package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenCustomFormCategory;
import com.lframework.starter.gen.vo.custom.form.category.CreateGenCustomFormCategoryVo;
import com.lframework.starter.gen.vo.custom.form.category.GenCustomFormCategorySelectorVo;
import com.lframework.starter.gen.vo.custom.form.category.UpdateGenCustomFormCategoryVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

/**
 * 自定义表单分类 Service
 *
 * @author zmj
 */
public interface IGenCustomFormCategoryService extends BaseMpService<GenCustomFormCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomFormCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenCustomFormCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomFormCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomFormCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenCustomFormCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenCustomFormCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
