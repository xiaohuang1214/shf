package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Date: 2022/5/25
 * Author:George
 * Description:
 */
public interface UserFollowMapper {

    /**
     * 根据userId和houseId查询关注
     * @param userId
     * @param houseId
     * @return
     */
    UserFollow findByUserIdAndHouseId(@Param("userId")Long userId,@Param("houseId")Long houseId);


    /**
     * 取消关注
     * @param userFollow
     */
    void update(UserFollow userFollow);


    /**
     * 添加关注
     * @param userFollow
     */
    void insert(UserFollow userFollow);


    Page<UserFollowVo> findListPage(@Param("userId") Long userId);


}
