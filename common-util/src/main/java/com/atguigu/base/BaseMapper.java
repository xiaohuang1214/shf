package com.atguigu.base;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */
public interface BaseMapper<T> {
    /**
     * 保存一个实体类
     * @param t
     */
    void insert(T t);

    /**
     * 批量添加
     * @param list
     */
    void inserBatch(List<T> list);

    /**
     * 删除
     * @param id 标识ID 可以是自增长ID,也可以是唯一标识
     */
    void delete(Long id);

    /**
     * 更新一个实体类
     * @param t
     */
    void update(T t);

    /**
     * 通过一个标识ID 获取一个唯一实体
     * @param id 标识ID 可以是自增长ID,也可以是唯一标识
     * @return
     */
    T getById(Long id);

    /**
     * 分页查询
     * @param filters
     * @return
     */
    Page<T> findPage(Map<String,Object> filters);
}
