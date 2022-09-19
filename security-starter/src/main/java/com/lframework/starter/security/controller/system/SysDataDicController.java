package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysDataDicService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.CreateSysDataDicVo;
import com.lframework.starter.mybatis.vo.system.dic.QuerySysDataDicVo;
import com.lframework.starter.mybatis.vo.system.dic.UpdateSysDataDicVo;
import com.lframework.starter.security.bo.system.dic.GetSysDataDicBo;
import com.lframework.starter.security.bo.system.dic.QuerySysDataDicBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典
 *
 * @author zmj
 */
@Api(tags = "数据字典")
@Validated
@RestController
@RequestMapping("/system/dic")
public class SysDataDicController extends DefaultBaseController {

  @Autowired
  private ISysDataDicService sysDataDicService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('system:dic:*')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysDataDicBo>> query(@Valid QuerySysDataDicVo vo) {
    PageResult<SysDataDic> pageResult = sysDataDicService.query(getPageIndex(vo), getPageSize(vo),
        vo);
    List<SysDataDic> datas = pageResult.getDatas();
    List<QuerySysDataDicBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:dic:*')")
  @GetMapping
  public InvokeResult<GetSysDataDicBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysDataDic data = sysDataDicService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典不存在！");
    }

    GetSysDataDicBo result = new GetSysDataDicBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据字典
   */
  @ApiOperation("新增数据字典")
  @PreAuthorize("@permission.valid('system:dic:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicVo vo) {

    sysDataDicService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典
   */
  @ApiOperation("修改数据字典")
  @PreAuthorize("@permission.valid('system:dic:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicVo vo) {

    sysDataDicService.update(vo);

    sysDataDicService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除数据字典
   */
  @ApiOperation("删除数据字典")
  @PreAuthorize("@permission.valid('system:dic:delete')")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    sysDataDicService.deleteById(id);

    sysDataDicService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
