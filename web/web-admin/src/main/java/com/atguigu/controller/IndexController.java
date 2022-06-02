package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date: 2022/5/27
 * Author:George
 * Description:
 */
@Controller
public class IndexController {
    private static final String SHOW_INDEX = "frame/index";
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    @RequestMapping("/")
    public String index(Model model){

        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.byUsername(user.getUsername());
        //查询用户权限列表
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin",admin);
        model.addAttribute("permissionList",permissionList);
        return SHOW_INDEX;
    }
}
