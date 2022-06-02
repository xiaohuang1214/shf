package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * Date: 2022/5/19
 * Author:George
 * Description:
 */
@Controller
@RequestMapping("/houseImage")
@PreAuthorize("hasAnyAuthority('house.editImage')")
public class  HouseImageController extends BaseController {
    private static final String PAGE_UPLOAD_SHOW = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house/show/";

    @Reference
    private HouseImageService houseImageService;

    @GetMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Model model){
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, @RequestParam("file") MultipartFile[]files) throws IOException {
        for (MultipartFile multipartFile : files) {
            //将文件上传到七牛云
            //获取文件名
            String filename = multipartFile.getOriginalFilename();
            //生成唯一文件名
            String uuidName = FileUtil.getUUIDName(filename);

            //将文件的URL保存到数据库
            String url = QiniuUtils.getUrl(uuidName);
            //创建houseImage对象
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setImageUrl(url);
            houseImage.setImageName(uuidName);
            houseImage.setType(type);
            houseImage.setCreateTime(new Date());
            houseImage.setUpdateTime(new Date());
            houseImage.setIsDeleted(0);
            //保存到数据库
            houseImageService.insert(houseImage);
            //上传文件
            //getBytes()将文件内容作为字节数组返回
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),uuidName);
        }
        return Result.ok();
    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("id")Long id){
        //从七牛云删除
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        //从数据库删除
        houseImageService.delete(id);

        return SHOW_ACTION + houseId;
    }
}
