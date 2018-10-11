package com.farmer.search.controller;

import com.farmer.pojo.SearchResult;
import com.farmer.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SearchController {

    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
//        1、接收参数
        queryString = new String(queryString.getBytes("iso-8859-1"), "utf-8");
//        2、调用服务查询商品列表
        SearchResult result = searchItemService.search(queryString, page, ITEM_ROWS);
//        3、把查询结果传递给页面。需要参数回显。
        //传递给页面
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);
        //返回逻辑视图
        return "search";
    }
}
