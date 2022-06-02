package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('admin.show')")
public class AdminController extends BaseController {
    private static final String PAGE_UPLOAD_SHOW = "admin/upload";
    private static final String PAGE_ASSIGN_SHOW = "admin/assignShow";

    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String LIST_ACTION = "redirect:/admin";

    @RequestMapping
    public String index(@RequestParam Map<String, Object> filters, Model model) {
        PageInfo<Admin> PageInfo = adminService.findPage(filters);
        model.addAttribute("page",PageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }

    @PostMapping("/save")
    public String save(Admin admin, Model model) {
        List<String> userNameList = adminService.findUserNameList();
        for (String username : userNameList) {
            if (username.equals(admin.getUsername())){
                return errorPage(model,"账号已被注册");
            }
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return successPage(model,"新增用户成功");
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Admin admin, Model model) {
        adminService.update(admin);
        return successPage(model,"编辑用户成功");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable("id")Long id,Model model){
        model.addAttribute(id);
        return PAGE_UPLOAD_SHOW;

    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable("id")Long id,Model model,@PathVariable("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String uuidName = FileUtil.getUUIDName(filename);

        String url = QiniuUtils.getUrl(uuidName);
        Admin admin = new Admin();
        admin.setHeadUrl(url);
        admin.setId(id);
        adminService.update(admin);

        //上传到七牛云
        QiniuUtils.upload2Qiniu(file.getBytes(),uuidName);

        return successPage(model,"上传头像成功");
    }


    @GetMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable("adminId")Long adminId,Model model){
        Map<String, List<Role>> map = roleService.findRoleIdListByAdminId(adminId);
        model.addAllAttributes(map);
        return PAGE_ASSIGN_SHOW;
    }

    @PostMapping("/assignRole")
    public String assignRole(@RequestParam("adminId")Long adminId,
                             @RequestParam("roleIds")List<Long> roleIdList,
                             Model model){
        roleService.saveAdminRole(adminId,roleIdList);
        return successPage(model,"保存成功");
    }

}
