package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Date: 2022/5/25
 * Author:George
 * Description:
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {
    @Reference
    private UserFollowService userFollowService;
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), houseId);
        if (userFollow != null) {
            userFollow.setIsDeleted(0);
            userFollowService.update(userFollow);
        }else {
            userFollow = new UserFollow();
            userFollow.setUserId(userInfo.getId());
            userFollow.setHouseId(houseId);
            userFollowService.insert(userFollow);
        }
        return Result.ok();
    }

    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable("pageNum")Integer pageNum,@PathVariable("pageSize")Integer pageSize,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }


    @GetMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id")Long id){
        UserFollow userFollow = new UserFollow();
        userFollow.setId(id);
        userFollow.setIsDeleted(1);
        userFollowService.update(userFollow);
        return Result.ok();
    }
}
