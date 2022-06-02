package com.atguigu.mapper;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
public interface DictMapper {

    /**
     * 根据父节点Id查子节点列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 判断是否是父节点
     * @param id
     * @return
     */
    Integer countIsParent(Long id);

    /**
     * 根据父节点的dictCode查询子节点列表
     * @param parentDictCode
     * @return
     */
    List<Dict> findDictListByParentDictCode(String parentDictCode);

}
