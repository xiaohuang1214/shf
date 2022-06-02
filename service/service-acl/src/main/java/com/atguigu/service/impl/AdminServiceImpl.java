package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */

@Service(interfaceClass = AdminService.class)
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public List<String> findUserNameList() {
        return adminMapper.findUserNameList();
    }

    @Override
    public Admin byUsername(String username) {
        return adminMapper.byUsername(username);
    }
}
