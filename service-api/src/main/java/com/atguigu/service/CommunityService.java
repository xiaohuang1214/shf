package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
