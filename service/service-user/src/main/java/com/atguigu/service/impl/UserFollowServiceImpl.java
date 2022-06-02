package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Date: 2022/5/25
 * Author:George
 * Description:
 */
@Transactional
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Override
    public UserFollow findByUserIdAndHouseId(Long userId, Long houseId) {
        return userFollowMapper.findByUserIdAndHouseId(userId, houseId);
    }

    @Override
    public void update(UserFollow userFollow) {
        userFollowMapper.update(userFollow);
    }

    @Override
    public void insert(UserFollow userFollow) {
        userFollowMapper.insert(userFollow);
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize,Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowMapper.findListPage(userId);
        return new PageInfo<>(page);
    }
}
