package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.entity.DefaultSysRoleMenu;
import com.lframework.starter.security.mappers.system.DefaultSysRoleMenuMapper;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.security.service.system.ISysRoleMenuService;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.vo.system.role.SysRoleMenuSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultSysRoleMenuServiceImpl implements ISysRoleMenuService {

    @Autowired
    private DefaultSysRoleMenuMapper defaultSysRoleMenuMapper;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @OpLog(type = OpLogType.OTHER, name = "角色授权菜单，角色ID：{}，菜单ID：{}", params = {"#vo.roleIds",
            "#vo.menuIds"}, loopFormat = true)
    @Transactional
    @Override
    public void setting(SysRoleMenuSettingVo vo) {

        for (String roleId : vo.getRoleIds()) {
            DefaultSysRoleDto role = sysRoleService.getById(roleId);
            if (ObjectUtil.isNull(role)) {
                throw new DefaultClientException("角色不存在！");
            }

            if (StringPool.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
                throw new DefaultClientException(
                        "角色【" + role.getName() + "】的权限为【" + StringPool.PERMISSION_ADMIN_NAME + "】，不允许授权！");
            }

            this.doSetting(roleId, vo.getMenuIds());
        }
    }

    protected void doSetting(String roleId, List<String> menuIds) {

        Wrapper<DefaultSysRoleMenu> deleteWrapper = Wrappers.lambdaQuery(DefaultSysRoleMenu.class)
                .eq(DefaultSysRoleMenu::getRoleId, roleId);
        defaultSysRoleMenuMapper.delete(deleteWrapper);

        if (!CollectionUtil.isEmpty(menuIds)) {
            Set<String> menuIdSet = new HashSet<>(menuIds);

            for (String menuId : menuIdSet) {
                DefaultSysMenuDto menu = sysMenuService.getById(menuId);
                if (ObjectUtil.isNull(menu)) {
                    throw new DefaultClientException("菜单不存在，请检查！");
                }

                DefaultSysRoleMenu record = new DefaultSysRoleMenu();
                record.setId(IdUtil.getId());
                record.setRoleId(roleId);
                record.setMenuId(menu.getId());

                defaultSysRoleMenuMapper.insert(record);
            }
        }
    }
}
