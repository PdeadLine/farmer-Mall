package com.farmer.controller;

import com.farmer.content.service.ContentService;
import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by sintai_zx
 * 2018/8/7 14:48
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUiDataGridResult getContentList(@RequestParam(value = "page",defaultValue = "0")Integer page,
                                               @RequestParam(value = "rows",defaultValue = "20")Integer rows,
                                               Long categoryId) {
     return    contentService.getPageContentListByCatId(page, rows, categoryId);
    }

    /**
        @Author sintai_zx
        @Date 2018/8/9 15:38
        @Discreption 新增内容
    */
    @RequestMapping("/save")
    @ResponseBody
    public FarmerResult saveContent(TbContent content) {
        return contentService.saveContent(content);
    }

    @RequestMapping("/rest/edit")
    @ResponseBody
    public FarmerResult editContent(TbContent content) {
        return contentService.editContent(content);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public FarmerResult deleteContent(String ids) {
        return contentService.deleteContent(ids);
    }
}
