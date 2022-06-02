package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * Date: 2022/5/24
 * Author:George
 * Description:
 */
public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);

    void lock(Long id, Integer status);
}
