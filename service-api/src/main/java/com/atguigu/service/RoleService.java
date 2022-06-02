package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/12
 * Author:George
 * Description:
 */
public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    Map<String,List<Role>> findRoleIdListByAdminId(Long adminId);

    /**
     * 添加角色
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRole(Long adminId ,List<Long> roleIdList);

}
