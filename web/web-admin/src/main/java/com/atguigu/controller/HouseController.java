package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022/5/18
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/house")
@PreAuthorize("hasAnyAuthority('house.show')")
public class HouseController extends BaseController {

    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;

    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters , Model model){
        //获取房源信息的分页数据
        PageInfo<House> page = houseService.findPage(filters);
        //存储域对象
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        //获取所有的字典列表，并存储到请求域中
        saveAllDictToRequestScope(model);
        return PAGE_INDEX;
    }

    /**
     * 获取所有的字典列表，并存储到请求域中
     * @param model
     */
    private void saveAllDictToRequestScope(Model model) {
        //查询小区列表,并储存到请求区域
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList", communityList);
        //查询房屋类型列表,并存储到请求域
        List<Dict> houseTypeList = dictService.findDictListByParentDictCode("houseType");
        model.addAttribute("houseTypeList", houseTypeList);
        //查询楼层列表,并存储到请求域
        List<Dict> floorList = dictService.findDictListByParentDictCode("floor");
        model.addAttribute("floorList", floorList);
        //查询建筑结构列表,并存储到请求域
        List<Dict> buildStructureList = dictService.findDictListByParentDictCode("buildStructure");
        model.addAttribute("buildStructureList", buildStructureList);
        //查询装修情况列表,并存储到请求域
        List<Dict> decorationList = dictService.findDictListByParentDictCode("decoration");
        model.addAttribute("decorationList", decorationList);
        //查询朝向列表,并存储到请求域
        List<Dict> directionList = dictService.findDictListByParentDictCode("direction");
        model.addAttribute("directionList", directionList);
        //查询房屋用途列表,并存储到请求域
        List<Dict> houseUseList = dictService.findDictListByParentDictCode("houseUse");
        model.addAttribute("houseUseList", houseUseList);

    }

    @RequestMapping("/create")
    public String create(Model model){
        saveAllDictToRequestScope(model);
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(House house,Model model){
        //未发布
        house.setStatus(HouseStatus.UNPUBLISHED.code);

        houseService.insert(house);
        return successPage(model,"添加房源信息成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        //获取所有
        saveAllDictToRequestScope(model);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(House house,Model model){
        houseService.update(house);
        return successPage(model,"修改房源信息成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        houseService.publish(id, status);
        return LIST_ACTION;
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Long id,Model model){
        //查询所有房源
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        //查询小区详情
        Community community = communityService.getById(house.getCommunityId());
        model.addAttribute("community",community);
        //房源信息图片
        List<HouseImage> houseImage1List = houseImageService.findHouseImageList(id, 1);
        model.addAttribute("houseImage1List",houseImage1List);
        //房产信息图片
        List<HouseImage> houseImage2List = houseImageService.findHouseImageList(id, 2);
        model.addAttribute("houseImage2List",houseImage2List);
        //经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        model.addAttribute("houseBrokerList",houseBrokerList);
        //房东信息
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        model.addAttribute("houseUserList",houseUserList);

        return PAGE_SHOW;
    }
}
