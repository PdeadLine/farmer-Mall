package com.farmer.controller;

import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.FarmerResult;
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
    /**
        @Author sintai_zx
        @Date 2018/8/1 11:49
        @Discreption 查询商品
    */
    @ResponseBody
    @RequestMapping("/item/{itemId}")
    public TbItem getItemByid(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    /**
        @Author sintai_zx
        @Date 2018/8/1 11:48
        @Discreption 商品列表
    */
    @ResponseBody
    @RequestMapping("/item/list")
    public EasyUiDataGridResult getItemList(int page,int rows) {
        return itemService.getItemList(page, rows);
    }
   /**
       @Author sintai_zx
       @Date 2018/8/2 16:46
       @Discreption 添加商品
   */
    @RequestMapping("/item/save")
    @ResponseBody
    public FarmerResult saveItem(TbItem item, String desc) {
        FarmerResult result = itemService.addItem(item, desc);
        return result;
    }

    @RequestMapping("/rest/item/update")
    @ResponseBody
    public FarmerResult editItem(TbItem item,String desc)
    {
        FarmerResult result = itemService.editItem(item, desc);
        return result;
    }
}
