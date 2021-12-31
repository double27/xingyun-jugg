package com.lframework.starter.security.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.security.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.security.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.security.entity.DefaultSysUserDept;
import com.lframework.starter.security.entity.DefaultSysUserPosition;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface DefaultSysUserDeptMapper extends BaseMapper<DefaultSysUserDept> {

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    List<DefaultSysUserDeptDto> getByUserId(String userId);

    /**
     * 根据部门ID查询是否存在
     * @param deptId
     * @return
     */
    DefaultSysUserDeptDto hasByDeptId(String deptId);
}
