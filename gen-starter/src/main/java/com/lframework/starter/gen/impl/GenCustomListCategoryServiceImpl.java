package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.entity.GenCustomListCategory;
import com.lframework.starter.gen.mappers.GenCustomListCategoryMapper;
import com.lframework.starter.gen.service.IGenCustomListCategoryService;
import com.lframework.starter.gen.service.IGenCustomListService;
import com.lframework.starter.gen.vo.custom.list.category.CreateGenCustomListCategoryVo;
import com.lframework.starter.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.starter.gen.vo.custom.list.category.UpdateGenCustomListCategoryVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListCategoryServiceImpl extends
    BaseMpServiceImpl<GenCustomListCategoryMapper, GenCustomListCategory> implements
    IGenCustomListCategoryService {

  @Autowired
  private IGenCustomListService genCustomListService;

  @Cacheable(value = GenCustomListCategory.CACHE_NAME, key = "'all'")
  @Override
  public List<GenCustomListCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenCustomListCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomListCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenCustomListCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomListCategory.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public GenCustomListCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateGenCustomListCategoryVo vo) {

    Wrapper<GenCustomListCategory> checkWrapper = Wrappers.lambdaQuery(GenCustomListCategory.class)
        .eq(GenCustomListCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomListCategory.class)
        .eq(GenCustomListCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomListCategory record = new GenCustomListCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateGenCustomListCategoryVo vo) {
    Wrapper<GenCustomListCategory> checkWrapper = Wrappers.lambdaQuery(GenCustomListCategory.class)
        .eq(GenCustomListCategory::getCode, vo.getCode())
        .ne(GenCustomListCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomListCategory.class)
        .eq(GenCustomListCategory::getName, vo.getName())
        .ne(GenCustomListCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomListCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    Wrapper<GenCustomList> queryWrapper = Wrappers.lambdaQuery(GenCustomList.class)
        .eq(GenCustomList::getCategoryId, id);
    if (genCustomListService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在自定义列表，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = GenCustomListCategory.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}