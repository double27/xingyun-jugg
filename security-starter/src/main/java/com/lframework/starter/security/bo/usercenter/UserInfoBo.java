package com.lframework.starter.security.bo.usercenter;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoBo extends BaseBo<UserInfoDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 编号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 性别
     */
    private Integer gender;

    public UserInfoBo() {

    }

    public UserInfoBo(UserInfoDto dto) {

        super(dto);
    }
}
