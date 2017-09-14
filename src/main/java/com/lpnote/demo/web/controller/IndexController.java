package com.lpnote.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by luopeng on 2017/9/13.
 */
@RequestMapping("/index")
@Controller
public class IndexController {

    @RequestMapping
    public String index(){
        return "index";
    }

}
