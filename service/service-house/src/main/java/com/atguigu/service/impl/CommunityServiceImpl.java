package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.mapper.CommunityMapper;
import com.atguigu.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Override
    protected BaseMapper<Community> getEntityMapper() {
        return communityMapper;
    }

    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }
}
