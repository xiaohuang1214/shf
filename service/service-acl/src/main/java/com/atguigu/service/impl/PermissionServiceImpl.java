package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date: 2022/5/26
 * Author:George
 * Description:
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionIdListByRoleId(Long roleId) {
        List<Permission> permissionList = permissionMapper.findAll();
        List<Long> permissionIdListByRoleId = rolePermissionMapper.findPermissionIdListByRoleId(roleId);

        List<Map<String, Object>> znodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            map.put("open",true);
            if (permissionIdListByRoleId.contains(permission.getId())){
                map.put("checked",true);
            }else{
                map.put("checked",false);
            }
            znodes.add(map);
        }

        return znodes;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIds) {
        List<Long> permissionIdListByRoleId = rolePermissionMapper.findPermissionIdListByRoleId(roleId);

        List<Long> removePermissionIdList = permissionIdListByRoleId.stream()
                .filter(item -> !permissionIds.contains(item)).collect(Collectors.toList());

        if (removePermissionIdList != null && removePermissionIdList.size()>0){
            rolePermissionMapper.removeRolePermission(roleId,removePermissionIdList);
        }

        for (Long permissionId : permissionIds) {
           RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionnId(roleId,permissionId);
            if (rolePermission == null) {
                rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }else{
                if (rolePermission.getIsDeleted() == 1) {
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            }
        }


    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        if (adminId == 1) {
            permissionList = permissionMapper.findAll();
        }else{
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissionList = permissionMapper.findAll();
        return PermissionHelper.build(permissionList);
    }

    @Override
    public Long countByParentId(Long id) {
        return permissionMapper.countByParentId(id);
    }

    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {
        List<String> list  = permissionMapper.findAllCodePermission();
        if (adminId == 1) {
            return list;
        }
        return permissionMapper.findCodePermissionListByAdminId(adminId);
    }
}
