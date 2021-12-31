package com.lframework.starter.web.service;

import com.lframework.starter.web.dto.MenuDto;

import java.util.List;
import java.util.Set;

/**
 * 菜单Service
 *
 * @author zmj
 */
public interface IMenuService extends BaseService {

    /**
     * 根据用户ID查询菜单
     * @param userId
     * @param isAdmin 是否为管理员
     * @return
     */
    List<MenuDto> getMenuByUserId(String userId, boolean isAdmin);

    /**
     * 根据用户ID查询权限
     * @param userId
     * @return
     */
    Set<String> getPermissionsByUserId(String userId);

    /**
     * 收藏菜单
     * @param userId
     * @param menuId
     */
    void collect(String userId, String menuId);

    /**
     * 取消收藏菜单
     * @param userId
     * @param menuId
     */
    void cancelCollect(String userId, String menuId);
}
