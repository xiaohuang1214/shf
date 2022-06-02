package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * Date: 2022/5/26
 * Author:George
 * Description:
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询所有权限列表
     * @return
     */
    List<Permission> findAll();

    List<Permission> findPermissionListByAdminId(Long adminId);

    Long countByParentId(Long id);

    List<String> findAllCodePermission();

    List<String> findCodePermissionListByAdminId(Long adminId);
}
