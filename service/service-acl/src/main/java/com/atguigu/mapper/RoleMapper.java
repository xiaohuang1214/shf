package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * Date: 2022/5/12
 * Author:George
 * Description:
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findAll();

}
