package com.atguigu.base;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */
//@Service
public abstract class BaseServiceImpl<T> {

    protected abstract BaseMapper<T> getEntityMapper();
    public void insert(T t){
        getEntityMapper().insert(t);
    }

    public void delete(Long id){
        getEntityMapper().delete(id);
    }

    public void update(T t){
        getEntityMapper().update(t);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T getById(Long id){
        return getEntityMapper().getById(id);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfo<T> findPage(Map<String, Object> filters){
        //当前页数据
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"),10);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityMapper().findPage(filters), 10);

    }


}
