package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findHouseImageList(Long houseId,Integer type);
}
