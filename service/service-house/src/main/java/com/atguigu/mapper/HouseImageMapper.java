package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
public interface HouseImageMapper extends BaseMapper<HouseImage> {
    List<HouseImage> findHouseImageList(@Param("houseId")Long houseId,@Param("type") Integer type);
}
