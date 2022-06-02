package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseUser;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.mapper.HouseUserMapper;
import com.atguigu.service.HouseService;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
@Service(interfaceClass = HouseUserService.class)
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserMapper houseUserMapper;

    @Override
    protected BaseMapper<HouseUser> getEntityMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> findHouseUserListByHouseId(long houseId) {
        return houseUserMapper.findHouseUserListByHouseId(houseId);
    }
}
