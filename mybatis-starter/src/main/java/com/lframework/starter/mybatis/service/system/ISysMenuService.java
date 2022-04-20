package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.mybatis.vo.system.menu.UpdateSysMenuVo;
import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public interface ISysMenuService extends BaseMpService<DefaultSysMenu> {

  /**
   * 系统菜单列表
   *
   * @return
   */
  List<DefaultSysMenuDto> queryList();

  /**
   * 根据角色ID查询已授权的菜单
   *
   * @param roleId
   * @return
   */
  List<DefaultSysMenuDto> getByRoleId(String roleId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysMenuDto findById(String id);

  /**
   * 创建系统菜单
   *
   * @param vo
   */
  String create(CreateSysMenuVo vo);

  /**
   * 修改系统菜单
   *
   * @param vo
   */
  void update(UpdateSysMenuVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 系统菜单选择器数据
   *
   * @return
   */
  List<DefaultSysMenuDto> selector(SysMenuSelectorVo vo);

  /**
   * 批量启用
   *
   * @param ids
   * @param userId
   */
  void batchEnable(List<String> ids, String userId);

  /**
   * 批量停用
   *
   * @param ids
   * @param userId
   */
  void batchUnable(List<String> ids, String userId);
}
