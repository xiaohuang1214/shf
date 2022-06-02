package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/23
 * Author:George
 * Description:
 */
@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private UserFollowService userFollowService;


    @PostMapping ("/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryBo houseQueryBo,@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryBo);
        return Result.ok(pageInfo);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id")Long id,HttpSession session){
        //1. 查询房源信息
        House house = houseService.getById(id);
        //2. 查询小区信息
        Community community = communityService.getById(house.getCommunityId());
        //3. 查询经纪人列表信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        //4. 获取房产列表信息
        List<HouseImage> houseImage1List = houseImageService.findHouseImageList(id, 1);
        //5.根据房源id查询房东列表
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        //6.查询当前用户是否已关注该房源
        //6.1 获取当前用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        Boolean isFollow = false;
        if (userInfo != null) {
            UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), id);
            if (userFollow != null && userFollow.getIsDeleted()==0){
                isFollow = true;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        map.put("houseUserList",houseUserList);
        map.put("isFollow",isFollow);
        return Result.ok(map);

    }






}
