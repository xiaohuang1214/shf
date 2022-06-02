package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    List<HouseBroker> findHouseBrokerListByHouseId(Long houseId);

    List<HouseBroker> findAll();
}
