package com.atguigu.service;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */
public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    List<String> findUserNameList();

    Admin byUsername(String username);
}
