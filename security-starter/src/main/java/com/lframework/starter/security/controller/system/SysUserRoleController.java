package com.lframework.starter.security.controller.system;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.bo.system.user.QueryUserRoleBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.service.system.ISysUserRoleService;
import com.lframework.starter.security.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.security.vo.system.user.SysUserRoleSettingVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户授权
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/user/role")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysUserRoleController extends DefaultBaseController {

  @Autowired
  private ISysRoleService sysRoleService;

  @Autowired
  private ISysUserRoleService sysUserRoleService;

  /**
   * 查询角色列表
   */
  @PreAuthorize("@permission.valid('system:user:permission')")
  @GetMapping("/roles")
  public InvokeResult roles(String userId) {

    List<QueryUserRoleBo> results = Collections.EMPTY_LIST;
    //查询所有角色
    QuerySysRoleVo sysRoleVo = new QuerySysRoleVo();
    sysRoleVo.setAvailable(Boolean.TRUE);
    List<DefaultSysRoleDto> allRole = sysRoleService.query(sysRoleVo);
    if (!CollectionUtil.isEmpty(allRole)) {
      results = allRole.stream().map(QueryUserRoleBo::new).collect(Collectors.toList());

      if (!StringUtil.isBlank(userId)) {
        List<DefaultSysRoleDto> menus = sysRoleService.getByUserId(userId);
        if (!CollectionUtil.isEmpty(menus)) {
          //当用户角色存在时，设置已选择属性
          for (QueryUserRoleBo result : results) {
            result.setSelected(
                menus.stream().anyMatch(t -> StringUtil.equals(t.getId(), result.getId())));
          }
        }
      }
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 用户授权
   */
  @PreAuthorize("@permission.valid('system:user:permission')")
  @PostMapping("/setting")
  public InvokeResult setting(@Valid @RequestBody SysUserRoleSettingVo vo) {

    sysUserRoleService.setting(vo);

    return InvokeResultBuilder.success();
  }
}
