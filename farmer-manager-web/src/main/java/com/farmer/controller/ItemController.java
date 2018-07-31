package com.farmer.controller;

import com.farmer.pojo.TbItem;
import com.farmer.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by sintai_zx
 * 2018/7/31 11:09
 */
@Controller
public class ItemController {
   @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/item/{itemId}")
    public TbItem getItemByid(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }
}
