package com.farmer.controller;

import com.farmer.pojo.EasyUITreeNode;
import com.farmer.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/2 8:06
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @ResponseBody
    @RequestMapping("/item/cat/list")
    public List<EasyUITreeNode> getCatList(@RequestParam(value = "id",defaultValue = "0")Long parentId) {
        List<EasyUITreeNode> easyUITreeNodeList = itemCatService.getCatList(parentId);
        return easyUITreeNodeList;
    }
}
