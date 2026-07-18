package com.library.back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/hello")
    public String sayHello() {
        return "後端基地連線成功！圖書管理系統準備就緒！";
    }

}
