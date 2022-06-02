package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> findZondes(Long id) {
        //根据父节点查子节点列表
        List<Dict> dictList = dictMapper.findListByParentId(id);
        //遍历出每一个子节点，转换成ztree需要的格式
//        List<Map<String, Object>> znodes1 = dictList.stream()
//                .map(dict -> {
//                    //转换成ztree需要的格式
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", dict.getId());
//                    map.put("name", dict.getName());
//                    map.put("isParent", !dictMapper.findListByParentId(dict.getId()).equals(0));
//                    return map;
//                })
//                .collect(Collectors.toList());

        List<Map<String, Object>> znodes = new ArrayList<>();
        for (Dict dict : dictList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("name", dict.getName());
            map.put("isParent", dictMapper.countIsParent(dict.getId()) > 0);
            znodes.add(map);
        }
        return znodes;
    }

    @Override
    public List<Dict> findDictListByParentDictCode(String parentDictCode) {
        Jedis jedis = null;
        try {
            //从redis中查找是否存有当前父节点的子节点列表
            jedis = jedisPool.getResource();
            String value = jedis.get("zfw:dict:parentDictCode:" + parentDictCode);
            if (!StringUtils.isEmpty(value)) {
                //redis中有数据，则将该数据转成List并返回
                return JSON.parseArray(value, Dict.class);
            }
            //redis中没有数据
            List<Dict> dictList = dictMapper.findDictListByParentDictCode(parentDictCode);
            //将DictList转成JSON并存储到redis中
            if (!CollectionUtils.isEmpty(dictList)) {
                jedis.set("zfw:dict:parentDictCode:" + parentDictCode, JSON.toJSONString(dictList));
            }
            return dictList;
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    jedis.disconnect();
                }
            }
        }
    }

    @Override
    public List<Dict> findDictListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            //从redis中查找是否存有当前父节点的子节点列表
            jedis = jedisPool.getResource();
            String value = jedis.get("zfw:dict:parentId:" + parentId);
            if (!StringUtils.isEmpty(value)) {
                //redis中有数据，则将该数据转成List并返回
                return JSON.parseArray(value, Dict.class);
            }
            //redis中没有数据，则从数据库查询
            List<Dict> dictList = dictMapper.findListByParentId(parentId);
            //将DictList转成JSON并存储到redis中
            if (!CollectionUtils.isEmpty(dictList)) {
                jedis.set("zfw:dict:parentId:" + parentId, JSON.toJSONString(dictList));
            }
            return dictList;
        } finally {
            //关闭jedis
            if (jedis != null) {
                jedis.close();
            }
            if (jedis.isConnected()) {
                jedis.disconnect();
            }
        }
    }
}
