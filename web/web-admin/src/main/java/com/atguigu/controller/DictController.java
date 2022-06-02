package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
@RestController//返回json字符串相当于@Controller+@ResponseBody
@RequestMapping("/dict")
@PreAuthorize("hasAnyAuthority('dict.show')")
public class DictController {
    @Reference
    private DictService dictService;

    @GetMapping("/findZnodes")
    public Result findZNodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        List<Map<String, Object>> zNodes = dictService.findZondes(id);
        return Result.ok(zNodes);
    }

    /**
     * 根据父节点的id获取子节点数据列表
     * @param parentId
     * @return
     */
    @GetMapping("/findDictListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId){
        List<Dict> dictList = dictService.findDictListByParentId(parentId);
        return Result.ok(dictList);
    }


    /**
     * 根据父节点的dictCode获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping("/findDictListByParentDictCode/{dictCode}")
    public Result<List<Dict>> findDictListByParentDictCode(@PathVariable String dictCode){
        List<Dict> list = dictService.findDictListByParentDictCode(dictCode);
        return Result.ok(list);
    }

}
