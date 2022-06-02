package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Date: 2022/5/29
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/userInfo")
@PreAuthorize("hasAnyAuthority('userInfo.show')")
public class InfoController extends BaseController {

    private static final String PAGE_INDEX = "userInfo/index";
    private static final String LIST_ACTION = "redirect:/userInfo";
    @Reference
    private UserInfoService userInfoService;

    @RequestMapping
    public String index(@RequestParam Map<String, Object> filters, Model model){
        if (!filters.containsKey("pageNum")){
            filters.put("pageNum",1);
        }
        if (!filters.containsKey("pageSize")){
            filters.put("pageSize",10);
        }
        PageInfo pageInfo = userInfoService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);

        return PAGE_INDEX;
    }

    @GetMapping("/lock/{id}/{status}")
    public String lock(@PathVariable("id")Long id,@PathVariable("status")Integer status){
        userInfoService.lock(id,status);
        return LIST_ACTION;
    }
}
