package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/12
 * Author:George
 * Description:
 */
@Service(interfaceClass = RoleService.class)
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Map<String, List<Role>> findRoleIdListByAdminId(Long adminId) {
        List<Role> roleList = roleMapper.findAll();
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdListByAdminId(adminId);
        ArrayList<Role> unAssignRoleList = new ArrayList<>();
        ArrayList<Role> assignRoleList = new ArrayList<>();
        for (Role role : roleList) {
            if (assignRoleIdList.contains(role.getId())){
                assignRoleList.add(role);
            }else{
                unAssignRoleList.add(role);
            }
        }

        Map<String, List<Role>> map = new HashMap<>();
        map.put("unAssignRoleList",unAssignRoleList);
        map.put("assignRoleList",assignRoleList);
        return map;
    }

    @Override
    public void saveAdminRole(Long adminId, List<Long> roleIdList) {
        //根据adminId查询所有roleId
        List<Long> roleIdListByAdminId = adminRoleMapper.findRoleIdListByAdminId(adminId);
        //找出要移除的角色
        List<Long> removeRoleIdList = new ArrayList<>();
        for (Long item : roleIdListByAdminId) {
            for (Long role : roleIdList) {
                if (!item.equals(role)){
                    removeRoleIdList.add(item);
                }
            }
        }
        //移除角色
        if (removeRoleIdList != null && removeRoleIdList.size()>0){
            adminRoleMapper.removeAdminRole(adminId, removeRoleIdList);
        }
        //添加角色
        for (Long roleId : roleIdList) {
            //查找是否存在
            AdminRole adminRole = adminRoleMapper.findByAdminIdAndRoleId(adminId, roleId);
            if (adminRole == null){
                adminRole = new AdminRole();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                adminRoleMapper.insert(adminRole);
            }else{
                if (adminRole.getIsDeleted() == 1){
                    adminRole.setIsDeleted(0);
                    adminRoleMapper.update(adminRole);
                }
            }
        }



    }

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return roleMapper;
    }
}
