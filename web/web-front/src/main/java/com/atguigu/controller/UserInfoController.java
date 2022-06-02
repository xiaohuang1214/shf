package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Date: 2022/5/24
 * Author:George
 * Description:
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
    @Reference
    private UserInfoService userInfoService;

    /**
     * 验证码
     * @param phone
     * @param session
     * @return
     */
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone")String phone, HttpSession session){
        String code = "1111";
        session.setAttribute("CODE",code);
        return Result.ok();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session){
        String code = (String) session.getAttribute("CODE");
        if (!code.equalsIgnoreCase(registerBo.getCode())){
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        if (userInfo != null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }


        userInfo = new UserInfo();
        String encryptPassword = MD5.encrypt(registerBo.getPassword());
        BeanUtils.copyProperties(registerBo,userInfo);
        userInfo.setPassword(encryptPassword);
        userInfo.setStatus(1);

        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result login(@RequestBody RegisterBo registerBo,HttpSession session){
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        if (userInfo == null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!MD5.encrypt(registerBo.getPassword()).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        if (userInfo.getStatus() == 0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        session.setAttribute("USER",userInfo);
//        Map<String, Object> map = new HashMap<>();
//        map.put("nickName",userInfo.getNickName());
//        map.put("phone",userInfo.getPhone());
        return Result.ok(userInfo);
    }

    @GetMapping("/logout")
    public Result logout(HttpSession session){
        session.invalidate();
        return Result.ok();
    }



}
