package com.iris.team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据信息Controller
 * 
 * @author whx
 * @date 2022-09-24
 */
@RestController
@RequestMapping("/team")
public class OhDataInfoController {

    @GetMapping("/foo")
    public String foo(){
        return "hello";
    }
}
