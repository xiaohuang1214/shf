package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/houseUser")
@PreAuthorize("hasAnyAuthority('house.editUser')")
public class HouseUserController extends BaseController {
    private static final String PAGE_CREATE = "houseUser/create";
    private static final String PAGE_EDIT = "houseUser/edit";
    private static final String SHOW_ACTION = "redirect:/house/show/";
    @Reference
    private HouseUserService houseUserService;

    @RequestMapping("/create")
    public String create(HouseUser houseUser, Model model){
        model.addAttribute("houseUser",houseUser);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(HouseUser houseUser,Model model){
        houseUserService.insert(houseUser);
        return successPage(model,"添加房东成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(HouseUser houseUser,Model model){
        houseUserService.update(houseUser);
        return successPage(model,"修改房东成功");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("id") Long id){
        houseUserService.delete(id);
        return SHOW_ACTION + houseId;
    }

}
