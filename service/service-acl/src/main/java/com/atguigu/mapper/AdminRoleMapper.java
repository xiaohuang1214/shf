package com.atguigu.mapper;

import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2022/5/26
 * Author:George
 * Description:
 */
public interface AdminRoleMapper {
    /**
     * 根据adminId查询所有roleId
     * @param adminId
     * @return
     */
    List<Long> findRoleIdListByAdminId(Long adminId);

    /**
     * 查询用户之前是否绑定过该角色
     * @param adminId
     * @param roleId
     * @return
     */
    AdminRole findByAdminIdAndRoleId(@Param("adminId")Long adminId,@Param("roleId")Long roleId);

    /**
     * 移除用户角色
     * @param adminId
     * @param removeRoleIdList
     */
    void removeAdminRole(@Param("adminId")Long adminId, @Param("roleIds")List<Long> removeRoleIdList);

    void insert(AdminRole adminRole);

    void update(AdminRole adminRole);
}
