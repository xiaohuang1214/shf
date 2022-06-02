package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date: 2022/5/27
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/permission")
@PreAuthorize("hasAnyAuthority('permission.show')")
public class PermissionController extends BaseController {

    private static final String PAGE_INDEX = "permission/index";
    private static final String PAGE_CREATE = "permission/create";
    private static final String LIST_ACTION = "redirect:/permission";
    private static final String PAGE_EDIT = "permission/edit";
    @Reference
    private PermissionService permissionService;

    @RequestMapping
    public String index(Model model){
       List<Permission> permissionList =  permissionService.findAll();
       model.addAttribute("list",permissionList);
       return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(Permission permission,Model model){
        model.addAttribute(permission);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
        return successPage(model,"新增菜单成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id,Model model){
        Long count = permissionService.countByParentId(id);
        if (count > 0){
            return errorPage(model,"菜单不为空无法删除");
        }
        permissionService.delete(id);
        return LIST_ACTION;
    }

    @RequestMapping("/edit/{id}")
    public String update(@PathVariable("id")Long id ,Model model){
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id")Long id, Permission permission,Model model){
        permission.setId(id);
        permissionService.update(permission);
        return successPage(model,"编辑菜单成功");
    }



}
