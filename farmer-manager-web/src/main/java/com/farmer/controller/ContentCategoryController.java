package com.farmer.controller;

import com.farmer.content.service.ContentCatService;
import com.farmer.pojo.EasyUITreeNode;
import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/7 14:40
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCatService contentCatService;

    /**
        @Author sintai_zx
        @Date 2018/8/7 15:26
        @Discreption 通过父类id查询内容分类信息
    */
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = contentCatService.getContentCatListById(parentId);
        return list;
    }

    /**
        @Author sintai_zx
        @Date 2018/8/8 14:32
        @Discreption 添加内容分类节点
    */
    @ResponseBody
    @RequestMapping("/create")
    public FarmerResult addContentCategory(Long parentId, String name) {
        FarmerResult result = contentCatService.addContentCategory(parentId, name);
        return result;
    }

    /**
        @Author sintai_zx
        @Date 2018/8/9 9:47
        @Discreption 更新节点
    */
    @RequestMapping("/update")
    @ResponseBody
    public FarmerResult updateContentCategory(Long id,String name) {
        return contentCatService.updateContentCatById(id, name);
    }
    /**
        @Author sintai_zx
        @Date 2018/8/9 9:49
        @Discreption 删除节点
    */
    @RequestMapping("/delete")
    @ResponseBody
    public FarmerResult deleteContentCattegory(Long id) {
        return contentCatService.deleteContentCatById(id);
    }
}
