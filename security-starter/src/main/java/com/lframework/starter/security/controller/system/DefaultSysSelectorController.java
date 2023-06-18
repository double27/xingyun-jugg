package com.lframework.starter.security.controller.system;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.entity.DefaultSysPosition;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.entity.SysDataDicCategory;
import com.lframework.starter.mybatis.entity.SysOpenDomain;
import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.SysModuleTenantService;
import com.lframework.starter.mybatis.service.TenantService;
import com.lframework.starter.mybatis.service.system.SysDataDicCategoryService;
import com.lframework.starter.mybatis.service.system.SysDataDicService;
import com.lframework.starter.mybatis.service.system.SysDeptService;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.mybatis.service.system.SysOpenDomainService;
import com.lframework.starter.mybatis.service.system.SysPositionService;
import com.lframework.starter.mybatis.service.system.SysRoleService;
import com.lframework.starter.mybatis.service.system.SysUserService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.SysDataDicSelectorVo;
import com.lframework.starter.mybatis.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.mybatis.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.starter.mybatis.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.mybatis.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.mybatis.vo.system.tenant.TenantSelectorVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.security.bo.system.dept.SysDeptSelectorBo;
import com.lframework.starter.security.bo.system.dic.SysDataDicSelectorBo;
import com.lframework.starter.security.bo.system.dic.category.SysDataDicCategorySelectorBo;
import com.lframework.starter.security.bo.system.menu.SysMenuSelectorBo;
import com.lframework.starter.security.bo.system.open.SysOpenDomainSelectorBo;
import com.lframework.starter.security.bo.system.position.SysPositionSelectorBo;
import com.lframework.starter.security.bo.system.role.SysRoleSelectorBo;
import com.lframework.starter.security.bo.system.tenant.TenantSelectorBo;
import com.lframework.starter.security.bo.system.user.SysUserSelectorBo;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.TenantUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class DefaultSysSelectorController extends DefaultBaseController {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysDeptService sysDeptService;

  @Autowired
  private SysPositionService sysPositionService;

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysDataDicCategoryService sysDataDicCategoryService;

  @Autowired
  private SysDataDicService sysDataDicService;

  @Autowired
  private SysOpenDomainService sysOpenDomainService;

  @Autowired
  private TenantService tenantService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 系统菜单
   */
  @ApiOperation("系统菜单")
  @GetMapping("/menu")
  public InvokeResult<List<SysMenuSelectorBo>> menu(@Valid SysMenuSelectorVo vo) {

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(TenantContextHolder.getTenantId());
    }

    List<SysMenuSelectorBo> results = CollectionUtil.emptyList();
    List<DefaultSysMenu> datas = sysMenuService.selector(vo, moduleIds);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysMenuSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("部门")
  @GetMapping("/dept")
  public InvokeResult<List<SysDeptSelectorBo>> dept() {

    List<SysDeptSelectorBo> results = CollectionUtil.emptyList();
    List<DefaultSysDept> datas = sysDeptService.selector();
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysDeptSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("角色")
  @GetMapping("/role")
  public InvokeResult<PageResult<SysRoleSelectorBo>> role(@Valid SysRoleSelectorVo vo) {

    PageResult<DefaultSysRole> pageResult = sysRoleService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<DefaultSysRole> datas = pageResult.getDatas();
    List<SysRoleSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysRoleSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载角色
   */
  @ApiOperation("加载角色")
  @PostMapping("/role/load")
  public InvokeResult<List<SysRoleSelectorBo>> loadRole(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<DefaultSysRole> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysRoleService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysRoleSelectorBo> results = datas.stream()
        .map(SysRoleSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("租户")
  @GetMapping("/tenant")
  public InvokeResult<PageResult<TenantSelectorBo>> tenant(@Valid TenantSelectorVo vo) {

    PageResult<Tenant> pageResult = tenantService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<Tenant> datas = pageResult.getDatas();
    List<TenantSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(TenantSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载租户
   */
  @ApiOperation("加载租户")
  @PostMapping("/tenant/load")
  public InvokeResult<List<TenantSelectorBo>> loadTenant(
      @RequestBody(required = false) List<Integer> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Tenant> datas = ids.stream().filter(Objects::nonNull)
        .map(t -> tenantService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<TenantSelectorBo> results = datas.stream()
        .map(TenantSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("用户")
  @GetMapping("/user")
  public InvokeResult<PageResult<SysUserSelectorBo>> user(@Valid SysUserSelectorVo vo) {

    PageResult<DefaultSysUser> pageResult = sysUserService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<DefaultSysUser> datas = pageResult.getDatas();
    List<SysUserSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysUserSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载用户
   */
  @ApiOperation("加载用户")
  @PostMapping("/user/load")
  public InvokeResult<List<SysUserSelectorBo>> loadUser(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<DefaultSysUser> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysUserService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysUserSelectorBo> results = datas.stream()
        .map(SysUserSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("岗位")
  @GetMapping("/position")
  public InvokeResult<PageResult<SysPositionSelectorBo>> position(@Valid SysPositionSelectorVo vo) {

    PageResult<DefaultSysPosition> pageResult = sysPositionService.selector(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<DefaultSysPosition> datas = pageResult.getDatas();
    List<SysPositionSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysPositionSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载岗位
   */
  @ApiOperation("加载岗位")
  @PostMapping("/position/load")
  public InvokeResult<List<SysPositionSelectorBo>> loadPosition(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<DefaultSysPosition> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysPositionService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysPositionSelectorBo> results = datas.stream()
        .map(SysPositionSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("数据字典分类")
  @GetMapping("/dic/category")
  public InvokeResult<PageResult<SysDataDicCategorySelectorBo>> dataDicCategory(
      @Valid SysDataDicCategorySelectorVo vo) {

    PageResult<SysDataDicCategory> pageResult = sysDataDicCategoryService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDicCategory> datas = pageResult.getDatas();
    List<SysDataDicCategorySelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysDataDicCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载数据字典分类
   */
  @ApiOperation("加载数据字典分类")
  @PostMapping("/dic/category/load")
  public InvokeResult<List<SysDataDicCategorySelectorBo>> loadDataDicCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysDataDicCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysDataDicCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysDataDicCategorySelectorBo> results = datas.stream()
        .map(SysDataDicCategorySelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("数据字典")
  @GetMapping("/dic")
  public InvokeResult<PageResult<SysDataDicSelectorBo>> dataDic(
      @Valid SysDataDicSelectorVo vo) {

    PageResult<SysDataDic> pageResult = sysDataDicService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDic> datas = pageResult.getDatas();
    List<SysDataDicSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysDataDicSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载数据字典
   */
  @ApiOperation("加载数据字典")
  @PostMapping("/dic/load")
  public InvokeResult<List<SysDataDicSelectorBo>> loadDataDic(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysDataDic> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysDataDicService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysDataDicSelectorBo> results = datas.stream()
        .map(SysDataDicSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 开放域
   */
  @ApiOperation("开放域")
  @GetMapping("/openDomain")
  public InvokeResult<PageResult<SysOpenDomainSelectorBo>> openDomain(
      @Valid SysOpenDomainSelectorVo vo) {

    PageResult<SysOpenDomain> pageResult = sysOpenDomainService.selector(vo);

    List<SysOpenDomain> datas = pageResult.getDatas();
    List<SysOpenDomainSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysOpenDomainSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载开放域
   */
  @ApiOperation("加载开放域")
  @PostMapping("/openDomain/load")
  public InvokeResult<List<SysOpenDomainSelectorBo>> openDomain(
      @RequestBody(required = false) List<Integer> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysOpenDomain> datas = ids.stream().filter(Objects::nonNull)
        .map(t -> sysOpenDomainService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SysOpenDomainSelectorBo> results = datas.stream().map(SysOpenDomainSelectorBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }
}
