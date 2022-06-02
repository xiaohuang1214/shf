package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2022/5/26
 * Author:George
 * Description:
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 根据roleId查询PermissionId集合
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdListByRoleId(Long roleId);

    void removeRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    RolePermission findByRoleIdAndPermissionnId(@Param("roleId")Long roleId,@Param("permissionId") Long permissionId);


}
