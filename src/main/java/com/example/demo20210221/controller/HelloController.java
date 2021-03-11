package com.example.demo20210221.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @ApiOperation(value = "欢迎回家")
    @GetMapping("/hello")
    public String hello(){
        return "欢迎回家";
    }
}
