package com.atguigu.controller;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
@PreAuthorize("hasAnyAuthority('community.show')")
public class CommunityController extends BaseController {

    private final static String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";

    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;


    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters , Model model){
        //处理filters中没有areaId和plateId的情况
        if (!filters.containsKey("areaId")){
            filters.put("areaId","");
        }
        if (!filters.containsKey("plateId")){
            filters.put("plateId","");
        }


        //按条件查询当前页的小区分页数据
        PageInfo<Community> page = communityService.findPage(filters);

        //加载北京的所有地区
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");

        //将数据存储到请求域
        model.addAttribute("page",page);
        model.addAttribute("areaList",areaList);
        model.addAttribute("filters",filters);

        return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(Model model){
        List<Dict> list = dictService.findDictListByParentDictCode("beijing");
        model.addAttribute("areaList",list);
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(Community community , Model model){
        communityService.insert(community);
        return successPage(model,"添加小区成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model , @PathVariable Long id){
        //查询小区信息
        Community community = communityService.getById(id);
        model.addAttribute("community",community);
        //查询beijing所有的区域
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Community community,Model model){
        communityService.update(community);
        return successPage(model,"修改小区信息成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        communityService.delete(id);
        return LIST_ACTION;
    }
}
