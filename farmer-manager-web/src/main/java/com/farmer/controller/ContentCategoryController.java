package com.farmer.controller;

import com.farmer.content.service.ContentCatService;
import com.farmer.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/7 14:40
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCatService contentCatService;

    /**
        @Author sintai_zx
        @Date 2018/8/7 15:26
        @Discreption 通过父类id查询内容分类信息
    */
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = contentCatService.getContentCatListById(parentId);
        return list;
    }
}
