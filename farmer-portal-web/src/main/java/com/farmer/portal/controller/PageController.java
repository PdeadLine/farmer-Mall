package com.farmer.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by sintai_zx
 * 2018/8/6 16:49
 */
@Controller
public class PageController {
    @RequestMapping("/index.html")
    public String showIndex() {
        return "index";
    }
}
