package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserInfo;

/**
 * Date: 2022/5/24
 * Author:George
 * Description:
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    /**
     * 根据用户手机号查询用户信息，如果手机号已存在，则不能注册
     * @param phone
     * @return
     */
    UserInfo getByPhone(String phone);

    void inster(UserInfo userInfo);
}
