package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Date: 2022/5/25
 * Author:George
 * Description:
 */
public interface UserFollowService {


    UserFollow findByUserIdAndHouseId(@Param("userId")Long userId, @Param("houseId")Long houseId);


    void update(UserFollow userFollow);


    void insert(UserFollow userFollow);

    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize,Long userId);

}
