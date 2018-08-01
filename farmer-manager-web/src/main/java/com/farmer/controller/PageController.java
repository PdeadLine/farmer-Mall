package com.farmer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by sintai_zx
 * 2018/8/1 9:19
 */
@Controller
public class PageController {

    /**
        @Author sintai_zx
        @Date 2018/8/1 9:21
        @Discreption 显示后台主页
    */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }
    /**
        @Author sintai_zx
        @Date 2018/8/1 9:24
        @Discreption 前端页面跳转
    */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
