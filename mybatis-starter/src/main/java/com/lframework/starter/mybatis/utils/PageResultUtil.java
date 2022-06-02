package com.lframework.starter.mybatis.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.BeanUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.NonNull;

/**
 * 分页数据Util
 *
 * @author zmj
 */
public class PageResultUtil {

  public static <T> PageResult<T> convert(@NonNull IPage<T> page) {

    return convert(page, null);
  }

  public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo) {

    return convert(pageInfo, null);
  }

  public static <T> PageResult<T> convert(@NotNull IPage<T> page,
      Map<Object, Object> extra) {
    PageResult<T> pageResult = new PageResult<>();
    List<T> datas = new ArrayList<T>(page.getRecords());
    pageResult.setDatas(datas);
    pageResult.setHasNext(page.getCurrent() > 1);
    pageResult.setHasPrev(page.getCurrent() < page.getPages());
    pageResult.setPageIndex(page.getCurrent());
    pageResult.setPageSize(page.getSize());
    pageResult.setTotalCount(page.getTotal());
    pageResult.setTotalPage((int) page.getPages());
    if (!ObjectUtil.isEmpty(extra)) {
      pageResult.setExtra(extra);
    }

    return pageResult;
  }

  public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo,
      Map<Object, Object> extra) {

    PageResult<T> pageResult = new PageResult<>();
    List<T> datas = new ArrayList<T>(pageInfo.getList());
    pageResult.setDatas(datas);
    pageResult.setHasNext(pageInfo.isHasNextPage());
    pageResult.setHasPrev(pageInfo.isHasPreviousPage());
    pageResult.setPageIndex((long) pageInfo.getPageNum());
    pageResult.setPageSize((long) pageInfo.getPageSize());
    pageResult.setTotalCount(pageInfo.getTotal());
    pageResult.setTotalPage(pageInfo.getPages());
    if (!ObjectUtil.isEmpty(extra)) {
      pageResult.setExtra(extra);
    }

    return pageResult;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T, S> PageResult<T> rebuild(@SuppressWarnings("rawtypes") PageResult pageResult,
      List<T> datas) {

    PageResult<T> result = new PageResult<>();
    BeanUtil.copyProperties(pageResult, result, "datas");

    result.setDatas(datas == null ? Collections.EMPTY_LIST : datas);

    pageResult.setDatas(datas);

    return result;
  }
}
