package com.farmer.controller;


import com.farmer.pojo.FarmerResult;
import com.farmer.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @Description:索引控制器
* @CopyRight (C)
* @author: SintaiZixun
* @date: 2018/10/8-14:22
*/

@Controller
public class IndexManagerController {
    @Autowired
    private SearchItemService searchItemService;


    /**
    * @Description: 导入索引库
    * @CopyRight (C)
    * @author: SintaiZixun
    * @date: 2018/10/8-15:12
    */
    @RequestMapping("/index/import")
    @ResponseBody
    public FarmerResult importIte() {
        FarmerResult farmerResult = searchItemService.importItemToIndex();
        return farmerResult;
    }
}
