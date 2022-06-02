package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:按条件分页加载房源
 */
public interface HouseMapper extends BaseMapper<House> {
    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
