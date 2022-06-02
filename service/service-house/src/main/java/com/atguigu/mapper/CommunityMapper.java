package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:加载所有小区
 */

public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> findAll();
}
