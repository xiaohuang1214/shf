package com.atguigu.service;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/26
 * Author:George
 * Description:
 */
public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionIdListByRoleId(Long roleId);

    void saveRolePermission(Long roleId, List<Long> permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long id);

    List<Permission> findAll();

    Long countByParentId(Long id);

    List<String> findCodePermissionListByAdminId(Long id);
}
