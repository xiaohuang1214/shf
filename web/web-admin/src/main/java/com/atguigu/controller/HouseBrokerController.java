package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/houseBroker")
@PreAuthorize("hasAnyAuthority('house.editBroker')")
public class HouseBrokerController extends BaseController {
    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/show/";
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;


    @RequestMapping("/create")
    public String create(HouseBroker houseBroker , Model model){
        //查询所有经纪人
        saveAdminListToModel(model);
        model.addAttribute("houseBroker",houseBroker);
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker,Model model){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.insert(houseBroker);
        return successPage(model,"添加经纪人成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        saveAdminListToModel(model);
        model.addAttribute("houseBroker",houseBroker);

        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Model model, HouseBroker houseBroker) {

        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        //修改经纪人信息
        houseBrokerService.update(houseBroker);

        return successPage(model, "修改经纪人成功");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") long houseId, @PathVariable("id") Long id){
        houseBrokerService.delete(id);
        return SHOW_ACTION + houseId;
    }

    private void saveAdminListToModel(Model model) {
        List<Admin> adminList = adminService.findAll();

        model.addAttribute("adminList",adminList);
    }
}
