package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Date: 2022/5/24
 * Author:George
 * Description:
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Reference
    private DictService dictService;

    @GetMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> list = dictService.findDictListByParentDictCode(dictCode);
        return Result.ok(list);
    }

    @GetMapping("/findListByParentId/{parentId}")
    public Result findListByParentId(@PathVariable("parentId")Long parentId){
        List<Dict> list = dictService.findDictListByParentId(parentId);
        return Result.ok(list);
    }
}
