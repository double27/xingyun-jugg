package com.lframework.starter.web.service;

import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;

/**
 * 用户Service
 *
 * @author zmj
 */
public interface IUserService extends BaseService {

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return
     */
    UserInfoDto getInfo(String userId);

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param password 新密码（明文）
     */
    void updatePassword(String userId, String password);

    /**
     * 修改邮箱
     * @param userId 用户ID
     * @param email 邮箱
     */
    void updateEmail(String userId, String email);

    /**
     * 修改联系电话
     * @param userId 用户ID
     * @param telephone 联系电话
     */
    void updateTelephone(String userId, String telephone);

    /**
     * 根据ID查询
     * 主要用于各个业务关联查询用户信息
     * @param id
     * @return
     */
    UserDto getById(String id);
}
